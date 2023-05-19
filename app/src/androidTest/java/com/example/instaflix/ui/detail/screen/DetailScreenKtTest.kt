package com.example.instaflix.ui.detail.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.ui.navigation.AppNavigation
import com.example.instaflix.ui.utils.Category
import com.example.instaflix.util.BaseUITest
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class DetailScreenKtTest : BaseUITest(dispatcherTest = QueueDispatcher()) {

    @Inject
    lateinit var filmDao: FilmDao

    @Before
    fun setUp() {
        every { filmDao.getFilmById(1) } answers {
            FilmEntity(
                id = 1,
                adult = false,
                title = "Ant-Man and the Wasp: Quantumania",
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
                category = Category.POPULAR,
            )
        }
    }

    @Test
    fun showScreenMovieDetailSuccess() {
        setMainContent(
            init = {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
                navController.navigate("detail/$1?")
            },
            block = {
                with(composeTestRule) {
                    waitUntilExists(
                        matcher = hasText("Ant-Man and the Wasp: Quantumania"),
                    )
                    onNodeWithText("Ant-Man and the Wasp: Quantumania").assertIsDisplayed()
                    onNodeWithText("Super-Hero partners Scott Lang and Hope van").assertIsDisplayed()
                    onNodeWithText("6.5").assertIsDisplayed()
                    onNodeWithText("2023-02-15").assertIsDisplayed()
                }
            },
        )
    }
}
