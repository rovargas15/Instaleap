package com.example.instaflix.ui.detail.delegate

import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.detail.state.FilmUiState
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FilmDetailDelegate(
    initialState: FilmUiState,
    private val getFilmsByIdUC: GetFilmsByIdUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<FilmUiState>(initialState, viewModelScope) {

    override fun createInitialState() = FilmsNowPlayingUiState(isLoading = true)

    fun fetchData(id: Long) {
        getFilmsByIdUC.invoke(id).map { result ->
            result.fold(
                onSuccess = { filmDetail ->
                    setState { copy(films = filmDetail, isLoading = false) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, errorObject = Pair(true, error.message)) }
                },
            )
        }.onStart {
            setState { FilmUiState().copy(isLoading = true) }
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
