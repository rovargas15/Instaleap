package com.example.instaflix.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.instaflix.BaseTest
import com.example.instaflix.DispatcherRule
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import com.example.instaflix.ui.home.state.OnTheAirSeriesUiState
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.home.state.TopRatedSeriesUiState
import com.example.instaflix.ui.home.state.UpcomingUiState
import com.example.instaflix.ui.utils.Route
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

class HomeFilmViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = DispatcherRule()

    @MockK
    lateinit var getFilmsByCategoryUC: GetFilmsByCategoryUC

    @MockK
    lateinit var getSeriesByCategoryUC: GetSeriesByCategoryUC

    private lateinit var viewModel: HomeFilmViewModel

    override fun setup() {
        super.setup()
        viewModel = HomeFilmViewModel(
            getFilmsByCategoryUC = getFilmsByCategoryUC,
            getSeriesByCategoryUC = getSeriesByCategoryUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher,
        )
    }

    @Test
    fun `give Route is MOVIE when invoke is called, then return films`() =
        mainDispatcherRule.runBlockingTest {
            // Give
            val films = mockk<List<Film>>()

            coEvery { getFilmsByCategoryUC.invoke(any()) } returns Result.success(films)

            // When
            viewModel.onLoad(Route.MOVIE)

            // Then
            val resultsPopular = mutableListOf<PopularFilmsUiState>()
            val resultsNowPlaying = mutableListOf<FilmsNowPlayingUiState>()
            val resultsUpcoming = mutableListOf<UpcomingUiState>()

            val jobPopular = launch {
                viewModel.popularFilmsMutableState().toList(resultsPopular)
            }
            val jobNowPlaying = launch {
                viewModel.filmNowPlayingMutableState().toList(resultsNowPlaying)
            }

            val jobUpcoming = launch {
                viewModel.upcomingFilmMutableState().toList(resultsUpcoming)
            }

            assert(resultsPopular[0].isLoading)
            assertEquals(resultsPopular[1].films, films)

            assert(resultsNowPlaying[0].isLoading)
            assertEquals(resultsNowPlaying[1].films, films)

            assert(resultsUpcoming[0].isLoading)
            assertEquals(resultsUpcoming[1].films, films)

            coVerify() {
                getFilmsByCategoryUC.invoke(any())
            }
            confirmVerified(getFilmsByCategoryUC)
            jobPopular.cancel()
            jobNowPlaying.cancel()
            jobUpcoming.cancel()
        }

    @Test
    fun `give Route is MOVIE when invoke is called, then return error`() =
        mainDispatcherRule.runBlockingTest {
            // Give
            val error = UnsatisfiedLinkError()

            coEvery { getFilmsByCategoryUC.invoke(any()) } returns Result.failure(error)

            // When
            viewModel.onLoad(Route.MOVIE)

            // Then
            val resultsPopular = mutableListOf<PopularFilmsUiState>()
            val resultsNowPlaying = mutableListOf<FilmsNowPlayingUiState>()
            val resultsUpcoming = mutableListOf<UpcomingUiState>()

            val jobPopular = launch {
                viewModel.popularFilmsMutableState().toList(resultsPopular)
            }
            val jobNowPlaying = launch {
                viewModel.filmNowPlayingMutableState().toList(resultsNowPlaying)
            }

            val jobUpcoming = launch {
                viewModel.upcomingFilmMutableState().toList(resultsUpcoming)
            }

            assert(resultsPopular[0].isLoading)
            assert(resultsPopular[1].isError())

            assert(resultsNowPlaying[0].isLoading)
            assert(resultsNowPlaying[1].isError())

            assert(resultsUpcoming[0].isLoading)
            assert(resultsUpcoming[1].isError())

            coVerify() {
                getFilmsByCategoryUC.invoke(any())
            }
            confirmVerified(getFilmsByCategoryUC)
            jobPopular.cancel()
            jobNowPlaying.cancel()
            jobUpcoming.cancel()
        }

    @Test
    fun `give Route is SERIEs when invoke is called, then return series`() =
        mainDispatcherRule.runBlockingTest {
            // Give
            val series = mockk<List<Series>>()

            coEvery { getSeriesByCategoryUC.invoke(any()) } returns Result.success(series)

            // When
            viewModel.onLoad(Route.SERIES)

            // Then
            // Then
            val resultsPopular = mutableListOf<PopularSeriesUiState>()
            val resultsTopRated = mutableListOf<TopRatedSeriesUiState>()
            val resultsOnTheAir = mutableListOf<OnTheAirSeriesUiState>()

            val jobPopular = launch {
                viewModel.popularSeriesMutableState().toList(resultsPopular)
            }
            val jobTopRated = launch {
                viewModel.topRatedSeriesMutableState().toList(resultsTopRated)
            }
            val jobOnTheAir = launch {
                viewModel.onTheAirSeriesMutableState().toList(resultsOnTheAir)
            }

            assert(resultsPopular[0].isLoading)
            assertEquals(resultsPopular[1].seriesList, series)

            assert(resultsTopRated[0].isLoading)
            assertEquals(resultsTopRated[1].seriesList, series)

            assert(resultsOnTheAir[0].isLoading)
            assertEquals(resultsOnTheAir[1].seriesList, series)

            coVerify() {
                getSeriesByCategoryUC.invoke(any())
            }
            confirmVerified(getSeriesByCategoryUC)
            jobPopular.cancel()
            jobTopRated.cancel()
            jobOnTheAir.cancel()
        }

    @Test
    fun `give Route is SERIEs when invoke is called, then return error`() =
        mainDispatcherRule.runBlockingTest {
            // Give
            val error = UnsatisfiedLinkError()

            coEvery { getSeriesByCategoryUC.invoke(any()) } returns Result.failure(error)

            // When
            viewModel.onLoad(Route.SERIES)

            // Then
            val resultsPopular = mutableListOf<PopularSeriesUiState>()
            val resultsTopRated = mutableListOf<TopRatedSeriesUiState>()
            val resultsOnTheAir = mutableListOf<OnTheAirSeriesUiState>()

            val jobPopular = launch {
                viewModel.popularSeriesMutableState().toList(resultsPopular)
            }
            val jobTopRated = launch {
                viewModel.topRatedSeriesMutableState().toList(resultsTopRated)
            }
            val jobOnTheAir = launch {
                viewModel.onTheAirSeriesMutableState().toList(resultsOnTheAir)
            }

            assert(resultsPopular[0].isLoading)
            assert(resultsPopular[1].isError())

            assert(resultsTopRated[0].isLoading)
            assert(resultsTopRated[1].isError())

            assert(resultsOnTheAir[0].isLoading)
            assert(resultsOnTheAir[1].isError())

            coVerify() {
                getSeriesByCategoryUC.invoke(any())
            }
            confirmVerified(getSeriesByCategoryUC)
            jobPopular.cancel()
            jobTopRated.cancel()
            jobOnTheAir.cancel()
        }
}
