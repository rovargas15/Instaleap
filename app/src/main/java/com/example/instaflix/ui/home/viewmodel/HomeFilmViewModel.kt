package com.example.instaflix.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.exception.TimeOutException
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.FilmResult
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
import com.example.instaflix.ui.home.state.HomeFilmState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeFilmViewModel @Inject constructor(
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val getLocalFilmsByCategoryUC: GetLocalFilmsByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _viewState = MutableLiveData<HomeFilmState>(null)
    val viewState: LiveData<HomeFilmState>
        get() = _viewState

    fun getFilmByCategory(category: String) {
        getFilmsByCategoryUC.invoke(category).map { result ->
            result.fold(
                onSuccess = { filmResult: FilmResult ->
                    _viewState.postValue(HomeFilmState.Success(filmResult.results))
                },
                onFailure = { error ->
                    if (error is TimeOutException) {
                        getLocalFilm(category)
                    } else {
                        _viewState.postValue(HomeFilmState.Error)
                    }
                },
            )
        }.onStart {
            _viewState.postValue(HomeFilmState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }

    private fun getLocalFilm(category: String) {
        getLocalFilmsByCategoryUC.invoke(category).map { result ->
            result.fold(
                onSuccess = { films: List<Film> ->
                    _viewState.postValue(HomeFilmState.Success(films))
                },
                onFailure = {
                    _viewState.postValue(HomeFilmState.Error)
                },
            )
        }.onStart {
            _viewState.postValue(HomeFilmState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
