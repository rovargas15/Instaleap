package com.example.instaflix.ui.home.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
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
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeFilmViewModel @Inject constructor(
    getFilmsByCategoryUC: GetFilmsByCategoryUC,
    getSeriesByCategoryUC: GetSeriesByCategoryUC,
    coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    /*
      Movie
     */
    private val upcomingDelegate = UpcomingFilmsDelegate(
        UpcomingUiState(isLoading = true),
        getFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val filmsNowPlayingDelegate = FilmsNowPlayingDelegate(
        FilmsNowPlayingUiState(isLoading = true),
        getFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    private val popularFilmsDelegate = PopularFilmsDelegate(
        PopularFilmsUiState(isLoading = true),
        getFilmsByCategoryUC,
        coroutineDispatcher,
        viewModelScope,
    )

    @VisibleForTesting
    internal fun upcomingFilmMutableState() = upcomingDelegate.viewState

    @VisibleForTesting
    internal fun filmNowPlayingMutableState() = filmsNowPlayingDelegate.viewState

    @VisibleForTesting
    internal fun popularFilmsMutableState() = popularFilmsDelegate.viewState

    val upcomingFilmState: StateFlow<UpcomingUiState>
        get() = upcomingFilmMutableState()
    val filmNowPlayingState: StateFlow<FilmsNowPlayingUiState>
        get() = filmNowPlayingMutableState()
    val popularFilmsState: StateFlow<PopularFilmsUiState>
        get() = popularFilmsMutableState()

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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun popularSeriesMutableState() = popularSeriesDelegate.viewState

    @VisibleForTesting
    internal fun topRatedSeriesMutableState() = topRatedSeriesDelegate.viewState

    @VisibleForTesting
    internal fun onTheAirSeriesMutableState() = onTheAirSeriesDelegate.viewState

    val popularSeriesState: StateFlow<PopularSeriesUiState>
        get() = popularSeriesMutableState()
    val topRatedSeriesState: StateFlow<TopRatedSeriesUiState>
        get() = topRatedSeriesMutableState()
    val onTheAirSeriesState: StateFlow<OnTheAirSeriesUiState>
        get() = onTheAirSeriesMutableState()

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
        if (route == Route.FILM) {
            onLoadMovie()
        } else {
            onLoadSeries()
        }
    }
}
