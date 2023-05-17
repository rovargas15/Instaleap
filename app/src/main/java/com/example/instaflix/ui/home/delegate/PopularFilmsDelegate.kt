package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.exception.InternetException
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PopularFilmsDelegate(
    initialState: PopularFilmsUiState,
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val getLocalFilmsByCategoryUC: GetLocalFilmsByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<PopularFilmsUiState>(initialState, viewModelScope) {

    override fun createInitialState() = PopularFilmsUiState(isLoading = true)

    fun fetchData() {
        setState { PopularFilmsUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getFilmsByCategoryUC.invoke(Category.POPULAR).fold(
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
        getLocalFilmsByCategoryUC.invoke(Category.POPULAR).map { result ->
            result.fold(
                onSuccess = { films: List<Film> ->
                    setState { copy(films = films, isLoading = false) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, errorObject = Pair(true, error.message)) }
                },
            )
        }.onStart {
            setState { PopularFilmsUiState().copy(isLoading = true) }
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
