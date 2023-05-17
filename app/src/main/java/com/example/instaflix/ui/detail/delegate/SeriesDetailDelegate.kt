package com.example.instaflix.ui.detail.delegate

import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.detail.state.SeriesUiState
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class SeriesDetailDelegate(
    initialState: SeriesUiState,
    private val getSeriesByIdUC: GetSeriesByIdUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<SeriesUiState>(initialState, viewModelScope) {

    override fun createInitialState() = FilmsNowPlayingUiState(isLoading = true)

    fun fetchData(id: Long) {
        getSeriesByIdUC.invoke(id).map { result ->
            result.fold(
                onSuccess = { series ->
                    setState { copy(series = series, isLoading = false) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, errorObject = Pair(true, error.message)) }
                },
            )
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
