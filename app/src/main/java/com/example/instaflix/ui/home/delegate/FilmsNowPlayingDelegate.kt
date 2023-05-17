package com.example.instaflix.ui.home.delegate

import android.util.Log
import com.example.instaflix.domain.exception.InternetException
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FilmsNowPlayingDelegate(
    initialState: FilmsNowPlayingUiState,
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val getLocalFilmsByCategoryUC: GetLocalFilmsByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<FilmsNowPlayingUiState>(initialState, viewModelScope) {

    override fun createInitialState() = FilmsNowPlayingUiState(isLoading = true)

    fun fetchData() {
        setState { FilmsNowPlayingUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            val response = getFilmsByCategoryUC.invoke(Category.PLAYING_NOW)
            Log.e("Error->>", response.getOrNull().toString())
            getFilmsByCategoryUC.invoke(Category.PLAYING_NOW).fold(
                onSuccess = { filmResult ->
                    setState { copy(films = filmResult.results, isLoading = false) }
                },
                onFailure = { error ->
                    if (error is InternetException) {
                        getLocalFilm()
                    } else {
                        setState {
                            copy(
                                isLoading = false,
                                errorObject = Pair(true, error.message),
                            )
                        }
                    }
                },
            )
        }
    }

    private fun getLocalFilm() {
        getLocalFilmsByCategoryUC.invoke(Category.PLAYING_NOW).map { result ->
            result.fold(
                onSuccess = { films: List<Film> ->
                    setState { copy(films = films, isLoading = false) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, errorObject = Pair(true, error.message)) }
                },
            )
        }.onStart {
            setState { FilmsNowPlayingUiState().copy(isLoading = true) }
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
