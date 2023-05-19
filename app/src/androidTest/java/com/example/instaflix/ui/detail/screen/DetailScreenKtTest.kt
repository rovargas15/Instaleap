package com.example.instaflix.ui.detail.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.ui.detail.state.FilmUiState
import com.example.instaflix.ui.detail.state.SeriesUiState
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel
import com.example.instaflix.ui.utils.Tag.BTN_BACK
import com.example.instaflix.ui.utils.Tag.ICON_STAR
import com.example.instaflix.ui.utils.Tag.IMG_BACKDROP
import com.example.instaflix.ui.utils.Tag.IMG_POSTER
import com.example.instaflix.util.BaseUITest
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class DetailScreenKtTest : BaseUITest(dispatcherTest = QueueDispatcher()) {

    @Test
    fun detailScreen_WithFilmState_DisplayFilmContent() {
        val film = Film(
            id = 1,
            adult = false,
            title = "Film Title",
            backdropPath = "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
            originalLanguage = "en",
            originalTitle = "Ant-Man and the Wasp: Quantumania",
            overview = "Super-Hero partners Scott Lang and Hope van",
            popularity = 4188.23,
            posterPath = "/A7SobaUTvb6d5Z3dpOhFxPG0RQf.jpg",
            releaseDate = "2023-02-15",
            video = false,
            voteAverage = 6.5,
            voteCount = 2555,
        )

        val viewModel = mockk<DetailFilmViewModel>(relaxed = true)
        every { viewModel.filmState } returns FilmUiState(film)
        every { viewModel.seriesState } returns SeriesUiState(null)

        setMainContent(init = {
            DetailScreen(detailFilmViewModel = viewModel) {}
        }, block = {
            with(composeTestRule) {
                waitUntilExists(
                    matcher = hasTestTag(BTN_BACK),
                )

                onNodeWithTag(IMG_BACKDROP, true).assertIsDisplayed()
                onNodeWithTag(IMG_POSTER, true).assertIsDisplayed()
                onNodeWithTag(ICON_STAR, true).assertIsDisplayed()
                onNodeWithText("Film Title").assertIsDisplayed()
                onNodeWithText("2023-02-15").assertIsDisplayed()
                onNodeWithText("6.5").assertIsDisplayed()
                onNodeWithText("Super-Hero partners Scott Lang and Hope van").assertIsDisplayed()
            }
        })
    }

    @Test
    fun detailScreen_WithSeriesState_DisplaySeriesContent() {
        val series = Series(
            id = 1,
            name = "Series Name",
            backdropPath = "/3CxUndGhUcZdt1Zggjdb2HkLLQX.jpg",
            originalLanguage = "en",
            originalName = "Ant-Man and the Wasp: Quantumania",
            overview = "Series Overview",
            popularity = 4188.23,
            posterPath = "/A7SobaUTvb6d5Z3dpOhFxPG0RQf.jpg",
            firstAirDate = "2023-05-19",
            voteAverage = 8.5,
            voteCount = 2555,
        )

        val filmState = FilmUiState(null)
        val seriesState = SeriesUiState(series)

        val viewModel = mockk<DetailFilmViewModel>(relaxed = true)
        coEvery { viewModel.filmState } returns filmState
        coEvery { viewModel.seriesState } returns seriesState

        setMainContent(init = {
            DetailScreen(detailFilmViewModel = viewModel) {}
        }, block = {
            with(composeTestRule) {
                waitUntilExists(
                    matcher = hasTestTag(BTN_BACK),
                )

                onNodeWithTag(IMG_BACKDROP, true).assertIsDisplayed()
                onNodeWithTag(IMG_POSTER, true).assertIsDisplayed()
                onNodeWithTag(ICON_STAR, true).assertIsDisplayed()
                onNodeWithText("Series Name").assertIsDisplayed()
                onNodeWithText("2023-05-19").assertIsDisplayed()
                onNodeWithText("8.5").assertIsDisplayed()
                onNodeWithText("Series Overview").assertIsDisplayed()
            }
        })
    }
}
