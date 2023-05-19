package com.example.instaflix.ui.home.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.domain.usecase.UpdateFilmsDataUC
import com.example.instaflix.domain.usecase.UpdateSeriesDataUC
import com.example.instaflix.ui.home.delegate.PopularFilmsDelegate
import com.example.instaflix.ui.home.delegate.PopularSeriesDelegate
import com.example.instaflix.ui.home.delegate.TopRatedSeriesDelegate
import com.example.instaflix.ui.home.delegate.UpcomingFilmsDelegate
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
    updateFilmsDataUC: UpdateFilmsDataUC,
    updateSeriesDataUC: UpdateSeriesDataUC,
    coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val upcomingDelegate: UpcomingFilmsDelegate
    private val popularFilmsDelegate: PopularFilmsDelegate
    private val popularSeriesDelegate: PopularSeriesDelegate
    private val topRatedSeriesDelegate: TopRatedSeriesDelegate

    init {
        /*Movie*/
        upcomingDelegate = UpcomingFilmsDelegate(
            UpcomingUiState(isLoading = true),
            getFilmsByCategoryUC,
            updateFilmsDataUC,
            coroutineDispatcher,
            viewModelScope,
        )

        popularFilmsDelegate = PopularFilmsDelegate(
            PopularFilmsUiState(isLoading = true),
            getFilmsByCategoryUC,
            updateFilmsDataUC,
            coroutineDispatcher,
            viewModelScope,
        )

        /*Series*/
        popularSeriesDelegate = PopularSeriesDelegate(
            PopularSeriesUiState(isLoading = true),
            getSeriesByCategoryUC,
            updateSeriesDataUC,
            coroutineDispatcher,
            viewModelScope,
        )

        topRatedSeriesDelegate = TopRatedSeriesDelegate(
            TopRatedSeriesUiState(isLoading = true),
            getSeriesByCategoryUC,
            updateSeriesDataUC,
            coroutineDispatcher,
            viewModelScope,
        )
    }

    @VisibleForTesting
    internal fun upcomingFilmMutableState() = upcomingDelegate.getMutableState()

    @VisibleForTesting
    internal fun popularFilmsMutableState() = popularFilmsDelegate.getMutableState()

    val upcomingFilmState: StateFlow<UpcomingUiState>
        get() = upcomingFilmMutableState()

    val popularFilmsState: StateFlow<PopularFilmsUiState>
        get() = popularFilmsMutableState()

    @VisibleForTesting
    internal fun popularSeriesMutableState() = popularSeriesDelegate.getMutableState()

    @VisibleForTesting
    internal fun topRatedSeriesMutableState() = topRatedSeriesDelegate.getMutableState()

    val popularSeriesState: StateFlow<PopularSeriesUiState>
        get() = popularSeriesMutableState()
    val topRatedSeriesState: StateFlow<TopRatedSeriesUiState>
        get() = topRatedSeriesMutableState()

    private fun onLoadMovie() {
        upcomingDelegate.fetchData()
        popularFilmsDelegate.fetchData()
    }

    private fun onLoadSeries() {
        popularSeriesDelegate.fetchData()
        topRatedSeriesDelegate.fetchData()
    }

    fun onLoad(route: String) {
        if (route == Route.FILM) {
            onLoadMovie()
        } else {
            onLoadSeries()
        }
    }
}
