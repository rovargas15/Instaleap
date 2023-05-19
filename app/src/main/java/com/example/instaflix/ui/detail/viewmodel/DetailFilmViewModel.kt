package com.example.instaflix.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import com.example.instaflix.ui.detail.delegate.FilmDetailDelegate
import com.example.instaflix.ui.detail.delegate.SeriesDetailDelegate
import com.example.instaflix.ui.detail.state.FilmUiState
import com.example.instaflix.ui.detail.state.SeriesUiState
import com.example.instaflix.ui.utils.Parameter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailFilmViewModel @Inject constructor(
    getFilmsByIdUC: GetFilmsByIdUC,
    getSeriesByIdUC: GetSeriesByIdUC,
    coroutineDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val filmDelegate = FilmDetailDelegate(
        FilmUiState(isLoading = true),
        getFilmsByIdUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val seriesDelegate = SeriesDetailDelegate(
        SeriesUiState(isLoading = true),
        getSeriesByIdUC,
        coroutineDispatcher,
        viewModelScope,
    )

    val filmState: FilmUiState
        get() = filmDelegate.getState()
    val seriesState: SeriesUiState
        get() = seriesDelegate.getState()

    fun onLoad() {
        savedStateHandle.get<String>(Parameter.FILM_ID)?.let { id ->
            val value = id.toLongOrNull()
            if (value != null) {
                filmDelegate.fetchData(value)
            }
        }
        savedStateHandle.get<String>(Parameter.SERIES_ID)?.let { id ->
            val value = id.toLongOrNull()
            if (value != null) {
                seriesDelegate.fetchData(value)
            }
        }
    }
}
