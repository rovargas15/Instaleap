package com.example.instaflix.ui.home.delegate

import android.util.Log
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.TopRatedSeriesUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TopRatedSeriesDelegate(
    initialState: TopRatedSeriesUiState,
    private val getSeriesByCategoryUC: GetSeriesByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<TopRatedSeriesUiState>(initialState, viewModelScope) {

    override fun createInitialState() = TopRatedSeriesUiState(isLoading = true)

    fun fetchData() {
        setState { TopRatedSeriesUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getSeriesByCategoryUC.invoke(Category.TOP_rated).fold(
                onSuccess = { filmResult ->
                    setState { copy(films = filmResult.results, isLoading = false) }
                },
                onFailure = { error ->
                    Log.e("error->>", "${error.message}")

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
