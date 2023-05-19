package com.example.instaflix.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.instaflix.HiltTestActivity
import com.example.instaflix.ui.theme.InstaflixTheme
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseUITest(private val dispatcherTest: Dispatcher) {

    @get:Rule(order = 1)
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule(order = 2)
    val composeTestRule by lazy { createAndroidComposeRule<HiltTestActivity>() }

    private lateinit var mockServer: MockWebServer

    init {
        startMockServer()
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun startMockServer() {
        mockServer = MockWebServer().apply {
            start(MOCK_WEB_SERVER_PORT)
            dispatcher = dispatcherTest
            Log.e("startMockServer", url("/test").toString())
        }
    }

    protected fun enqueueResponses(vararg response: MockResponse) {
        response.forEach { mockServer.enqueue(it) }
    }

    @After
    open fun tearDown() {
        mockServer.shutdown()
    }

    fun setMainContent(
        init: @Composable () -> Unit,
        block: () -> Unit,
    ) {
        composeTestRule.setContent {
            InstaflixTheme() {
                init()
            }
        }
        block()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeContentTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeoutMillis: Long = 5_000L,
    ) {
        return this.waitUntilNodeCount(matcher, 2, timeoutMillis)
    }
}

private const val MOCK_WEB_SERVER_PORT = 8080
