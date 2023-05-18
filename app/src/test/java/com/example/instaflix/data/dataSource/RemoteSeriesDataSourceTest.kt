package com.example.instaflix.data.dataSource

import com.example.instaflix.BaseTest
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteSeriesDataSourceTest : BaseTest() {

    @MockK
    lateinit var api: SeriesApi

    private lateinit var dataSource: RemoteSeriesDataSource

    @Before
    override fun setup() {
        super.setup()
        dataSource = RemoteSeriesDataSource(
            api = api,
        )
    }

    @Test
    fun `getSeries should return films from API`() = runBlocking {
        // Given
        val category = "action"
        val series: BaseResponse<SeriesResponse> = mockk()
        coEvery { api.getSeries(category) } returns series

        // When
        val result = dataSource.getSeries(category)

        // Then
        TestCase.assertEquals(series, result)
        coVerify { api.getSeries(category) }
        confirmVerified(api)
    }
}
