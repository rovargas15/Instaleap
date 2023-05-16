package com.example.instaflix.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.ui.detail.state.DetailFilmState
import com.example.instaflix.ui.utils.Parameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailFilmViewModel @Inject constructor(
    private val getFilmsByIdUC: GetFilmsByIdUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _viewState = MutableLiveData<DetailFilmState>()
    val viewState: LiveData<DetailFilmState>
        get() = _viewState

    init {
        savedStateHandle.get<Long>(Parameter.FILM_ID)?.let {
            getMovieById(it)
        }
    }

    private fun getMovieById(filmId: Long) {
        getFilmsByIdUC.invoke(filmId).map { result ->
            result.fold(
                onSuccess = { film: Film ->
                    _viewState.postValue(DetailFilmState.Success(film))
                },
                onFailure = {
                    _viewState.postValue(DetailFilmState.Error)
                },
            )
        }.onStart {
            _viewState.postValue(DetailFilmState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
