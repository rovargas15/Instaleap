package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.UpdateFilmsDataUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PopularFilmsDelegate(
    initialState: PopularFilmsUiState,
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val updateFilmsDataUC: UpdateFilmsDataUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<PopularFilmsUiState>(initialState, viewModelScope) {

    override fun createInitialState() = PopularFilmsUiState(isLoading = true)

    fun fetchData() {
        viewModelScope.launch(coroutineDispatcher) {
            launch {
                fetchDataLocal()
            }
            launch {
                fetchDataWithRetry()
            }
        }
    }

    private suspend fun fetchDataLocal() {
        getFilmsByCategoryUC.invoke(Category.POPULAR).onStart {
            setState { PopularFilmsUiState().copy(isLoading = true) }
        }.collect { result ->
            result.fold(
                onSuccess = { filmResult ->
                    if (filmResult.isNotEmpty()) {
                        setState { copy(films = filmResult, isLoading = false) }
                    }
                },
                onFailure = { error ->
                    setState {
                        copy(
                            isLoading = false,
                            errorObject = Pair(true, error.message),
                        )
                    }
                },
            )
        }
    }

    private suspend fun fetchDataWithRetry() {
        var retryCount = 0
        val maxRetries = 3

        while (retryCount <= maxRetries) {
            val result = updateFilmsDataUC.invoke(Category.POPULAR)
            if (result.isFailure) {
                if (retryCount < maxRetries) {
                    retryCount++
                    delay(1_000)
                    if (getState().films.isEmpty()) {
                        setState {
                            copy(
                                isLoading = false,
                                errorObject = Pair(
                                    true,
                                    result.exceptionOrNull()?.message ?: "",
                                ),
                            )
                        }
                    }
                } else {
                    break
                }
            } else {
                break
            }
        }
    }
}
