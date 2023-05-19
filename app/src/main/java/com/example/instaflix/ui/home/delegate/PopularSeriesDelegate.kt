package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.domain.usecase.UpdateSeriesDataUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PopularSeriesDelegate(
    initialState: PopularSeriesUiState,
    private val getSeriesByCategoryUC: GetSeriesByCategoryUC,
    private val updateSeriesDataUC: UpdateSeriesDataUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<PopularSeriesUiState>(initialState, viewModelScope) {

    override fun createInitialState() = PopularSeriesUiState(isLoading = true)

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
        getSeriesByCategoryUC.invoke(Category.POPULAR).onStart {
            setState { PopularSeriesUiState().copy(isLoading = true) }
        }.collect { result ->
            result.fold(
                onSuccess = { seriesResult ->
                    if (seriesResult.isNotEmpty()) {
                        setState { copy(seriesList = seriesResult, isLoading = false) }
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
            val result = updateSeriesDataUC.invoke(Category.POPULAR)
            if (result.isFailure) {
                if (retryCount < maxRetries) {
                    retryCount++
                    delay(1_000)
                    if (getState().seriesList.isEmpty()) {
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
