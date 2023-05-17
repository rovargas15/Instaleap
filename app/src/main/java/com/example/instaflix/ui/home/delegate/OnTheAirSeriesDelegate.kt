package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.OnTheAirSeriesUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OnTheAirSeriesDelegate(
    initialState: OnTheAirSeriesUiState,
    private val getSeriesByCategoryUC: GetSeriesByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<OnTheAirSeriesUiState>(initialState, viewModelScope) {

    override fun createInitialState() = OnTheAirSeriesUiState(isLoading = true)

    fun fetchData() {
        setState { OnTheAirSeriesUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getSeriesByCategoryUC.invoke(Category.ON_THE_AIR).fold(
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
