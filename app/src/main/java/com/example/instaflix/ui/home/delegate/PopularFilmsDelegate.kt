package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularFilmsDelegate(
    initialState: PopularFilmsUiState,
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<PopularFilmsUiState>(initialState, viewModelScope) {

    override fun createInitialState() = PopularFilmsUiState(isLoading = true)

    fun fetchData() {
        setState { PopularFilmsUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getFilmsByCategoryUC.invoke(Category.POPULAR).fold(
                onSuccess = { filmResult ->
                    setState { copy(films = filmResult, isLoading = false) }
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
}
