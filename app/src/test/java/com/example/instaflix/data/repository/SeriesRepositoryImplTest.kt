package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.dataSource.LocalSeriesDataSource
import com.example.instaflix.data.dataSource.RemoteSeriesDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.exception.PermissionDeniedException
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

class SeriesRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var remoteSeriesDataSource: RemoteSeriesDataSource

    @MockK
    lateinit var localSeriesDataSource: LocalSeriesDataSource

    private lateinit var repository: SeriesRepository

    @Before
    override fun setup() {
        super.setup()
        repository = SeriesRepositoryImpl(
            remoteSeriesDataSource = remoteSeriesDataSource,
            localSeriesDataSource = localSeriesDataSource,
        )
    }

    @Test
    fun `give category, when getSeries is called, then return success result`() = runBlocking {
        // Given
        val series: List<Series> = mockk()
        val category = "action"

        coEvery { localSeriesDataSource.getAllSeries(category) } returns flowOf(series)

        // When
        val result = repository.getSeries(category).single().getOrNull()

        // Then
        assertEquals(series, result)
        coVerify {
            localSeriesDataSource.getAllSeries(category)
        }
        confirmVerified(localSeriesDataSource)
    }

    @Test
    fun `give category, when updateSeries is called, then return success result`() = runBlocking {
        // Given
        val baseResponse: BaseResponse<SeriesResponse> = mockk()
        val seriesResponse: List<SeriesResponse> = mockk()
        val category = "action"

        mockkStatic(baseResponse::mapToBaseResult)

        every { baseResponse.results } returns seriesResponse
        coEvery { remoteSeriesDataSource.getSeries(category) } returns baseResponse
        coEvery {
            localSeriesDataSource.insertSeries(seriesResponse, category)
        } just runs

        // When
        val result = repository.updateSeries(category)

        // Then
        assertEquals(true, result.isSuccess)
        coVerify {
            remoteSeriesDataSource.getSeries(category)
            localSeriesDataSource.insertSeries(seriesResponse, category)
        }
        verify {
            baseResponse.results
        }
        confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
    }

    @Test
    fun `give id, when getSeriesById is called, then return success result`() = runBlocking {
        // Given
        val id = 1L
        val series: Series = mockk()

        coEvery {
            localSeriesDataSource.getSeriesById(id)
        } returns series

        // When
        val result = repository.getSeriesById(id).getOrNull()

        // Then
        assertEquals(series, result)
        coVerify {
            localSeriesDataSource.getSeriesById(id)
        }
        confirmVerified(localSeriesDataSource)
    }

    @Test
    fun `give HttpException HTTP_UNAUTHORIZED, when updateSeries is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED
            coEvery { remoteSeriesDataSource.getSeries(category) } throws error

            // When
            val result = repository.updateSeries(category).exceptionOrNull()

            // Then
            assertEquals(PermissionDeniedException(), result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
            }
            confirmVerified(remoteSeriesDataSource)
        }
}
