package com.example.instaflix.ui.home.screen

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.util.BaseUITest
import com.example.instaflix.util.FILE_SUCCESS_RESPONSE
import com.example.instaflix.util.FILE_SUCCESS_SERIES_RESPONSE
import com.example.instaflix.util.mockResponse
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class HomeScreenKtTest : BaseUITest(dispatcherTest = QueueDispatcher()) {

    private val successResponse: MockResponse
        get() = mockResponse(FILE_SUCCESS_RESPONSE, HttpURLConnection.HTTP_OK)
    private val successSeriesResponse: MockResponse
        get() = mockResponse(FILE_SUCCESS_SERIES_RESPONSE, HttpURLConnection.HTTP_OK)

    @Test
    fun homeScreen_WithFilmState_DisplayContentMovie() {
        enqueueResponses(successResponse)
        val viewModel = mockk<HomeFilmViewModel>(relaxed = true)
    }
}
