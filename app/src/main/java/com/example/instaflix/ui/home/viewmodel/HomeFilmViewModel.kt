package com.example.instaflix.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.ui.home.delegate.FilmsNowPlayingDelegate
import com.example.instaflix.ui.home.delegate.OnTheAirSeriesDelegate
import com.example.instaflix.ui.home.delegate.PopularFilmsDelegate
import com.example.instaflix.ui.home.delegate.PopularSeriesDelegate
import com.example.instaflix.ui.home.delegate.TopRatedSeriesDelegate
import com.example.instaflix.ui.home.delegate.UpcomingFilmsDelegate
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import com.example.instaflix.ui.home.state.OnTheAirSeriesUiState
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.home.state.TopRatedSeriesUiState
import com.example.instaflix.ui.home.state.UpcomingUiState
import com.example.instaflix.ui.utils.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class HomeFilmViewModel @Inject constructor(
    getFilmsByCategoryUC: GetFilmsByCategoryUC,
    getSeriesByCategoryUC: GetSeriesByCategoryUC,
    getLocalFilmsByCategoryUC: GetLocalFilmsByCategoryUC,
    coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {
    /*
      Movie
     */
    private val upcomingDelegate = UpcomingFilmsDelegate(
        UpcomingUiState(isLoading = true),
        getFilmsByCategoryUC,
        getLocalFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val filmsNowPlayingDelegate = FilmsNowPlayingDelegate(
        FilmsNowPlayingUiState(isLoading = true),
        getFilmsByCategoryUC,
        getLocalFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val popularFilmsDelegate = PopularFilmsDelegate(
        PopularFilmsUiState(isLoading = true),
        getFilmsByCategoryUC,
        getLocalFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    val upcomingFilmState get() = upcomingDelegate.getState()
    val filmNowPlayingState get() = filmsNowPlayingDelegate.getState()
    val popularFilmsState get() = popularFilmsDelegate.getState()

    /*
      Series
     */
    private val popularSeriesDelegate = PopularSeriesDelegate(
        PopularSeriesUiState(isLoading = true),
        getSeriesByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val topRatedSeriesDelegate = TopRatedSeriesDelegate(
        TopRatedSeriesUiState(isLoading = true),
        getSeriesByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val onTheAirSeriesDelegate = OnTheAirSeriesDelegate(
        OnTheAirSeriesUiState(isLoading = true),
        getSeriesByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    val popularSeriesState get() = popularSeriesDelegate.getState()
    val topRatedSeriesPlayingState get() = topRatedSeriesDelegate.getState()
    val onTheAirSeriesState get() = onTheAirSeriesDelegate.getState()

    private fun onLoadMovie() {
        upcomingDelegate.fetchData()
        filmsNowPlayingDelegate.fetchData()
        popularFilmsDelegate.fetchData()
    }

    private fun onLoadSeries() {
        popularSeriesDelegate.fetchData()
        topRatedSeriesDelegate.fetchData()
        onTheAirSeriesDelegate.fetchData()
    }

    fun onLoad(route: String) {
        if (route == Route.MOVIE) {
            onLoadMovie()
        } else {
            onLoadSeries()
        }
    }
}
