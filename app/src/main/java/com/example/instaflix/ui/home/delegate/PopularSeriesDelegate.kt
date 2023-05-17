package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularSeriesDelegate(
    initialState: PopularSeriesUiState,
    private val getSeriesByCategoryUC: GetSeriesByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<PopularSeriesUiState>(initialState, viewModelScope) {

    override fun createInitialState() = PopularSeriesUiState(isLoading = true)

    fun fetchData() {
        setState { PopularSeriesUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getSeriesByCategoryUC.invoke(Category.POPULAR).fold(
                onSuccess = { filmResult ->
                    setState { copy(films = filmResult.results, isLoading = false) }
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
