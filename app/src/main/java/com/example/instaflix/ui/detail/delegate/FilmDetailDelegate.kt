package com.example.instaflix.ui.detail.delegate

import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.detail.state.FilmUiState
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FilmDetailDelegate(
    initialState: FilmUiState,
    private val getFilmsByIdUC: GetFilmsByIdUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<FilmUiState>(initialState, viewModelScope) {

    override fun createInitialState() = FilmsNowPlayingUiState(isLoading = true)

    fun fetchData(id: Long) {
        setState { FilmUiState().copy(isLoading = true) }
        viewModelScope.launch(coroutineDispatcher) {
            getFilmsByIdUC.invoke(id).fold(
                onSuccess = { filmDetail ->
                    setState { copy(film = filmDetail, isLoading = false) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, errorObject = Pair(true, error.message)) }
                },
            )
        }
    }
}
