package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.dataSource.LocalSeriesDataSource
import com.example.instaflix.data.dataSource.RemoteSeriesDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.exception.PermissionDeniedException
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
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
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
        val baseResponse: BaseResponse<SeriesResponse> = mockk()
        val expectedBaseResult: BaseResult<Series> = mockk()
        val seriesResponse: List<SeriesResponse> = mockk()
        val expectedSeries: List<Series> = mockk()
        val category = "action"

        mockkStatic(baseResponse::mapToBaseResult)

        every { baseResponse.results } returns seriesResponse
        every { baseResponse.mapToBaseResult() } returns expectedBaseResult
        every { expectedBaseResult.results } returns expectedSeries

        coEvery { remoteSeriesDataSource.getSeries(category) } returns baseResponse
        coEvery {
            localSeriesDataSource.insertSeries(seriesResponse, category)
        } just runs

        // When
        val result = repository.getSeries(category).getOrNull()

        // Then
        assertEquals(expectedSeries, result)
        coVerify {
            remoteSeriesDataSource.getSeries(category)
            localSeriesDataSource.insertSeries(seriesResponse, category)
        }
        verify {
            baseResponse.results
            baseResponse.mapToBaseResult()
            expectedBaseResult.results
        }
        confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
    }

    @Test
    fun `give HttpException HTTP_UNAUTHORIZED, when getSeries is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED
            coEvery { remoteSeriesDataSource.getSeries(category) } throws error

            // When
            val result = repository.getSeries(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
            }
            confirmVerified(remoteSeriesDataSource)
        }

    @Test
    fun `give Exception, when insertSeries is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val seriesResponse: List<SeriesResponse> = mockk()
            val baseResponse: BaseResponse<SeriesResponse> = mockk()
            val category = "action"

            mockkStatic(baseResponse::mapToBaseResult)

            coEvery { remoteSeriesDataSource.getSeries(category) } returns baseResponse
            coEvery {
                localSeriesDataSource.insertSeries(
                    seriesResponse,
                    category,
                )
            } throws Throwable()
            every { baseResponse.results } returns seriesResponse

            // When
            val result = repository.getSeries(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
                localSeriesDataSource.insertSeries(seriesResponse, category)
            }
            confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
        }

    @Test
    fun `give HttpException HTTP_FORBIDDEN, when getSeries is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_FORBIDDEN
            coEvery { remoteSeriesDataSource.getSeries(category) } throws error

            // When
            val result = repository.getSeries(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
            }
            confirmVerified(remoteSeriesDataSource)
        }

    @Test
    fun `give SocketTimeoutException, when getSeries is called, then result Success`() =
        runBlocking {
            // Given
            val error: SocketTimeoutException = mockk()
            val category = "action"
            val series: List<Series> = mockk()

            coEvery { remoteSeriesDataSource.getSeries(category) } throws error
            coEvery { localSeriesDataSource.getAllSeries(category) } returns series

            // When
            val result = repository.getSeries(category).getOrNull()

            // Then
            assertEquals(series, result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
                localSeriesDataSource.getAllSeries(category)
            }
            confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
        }

    @Test
    fun `give ConnectException, when getSeries is called, then return result Success`() =
        runBlocking {
            // Given
            val error: ConnectException = mockk()
            val category = "action"
            val series: List<Series> = mockk()

            coEvery { remoteSeriesDataSource.getSeries(category) } throws error
            coEvery { localSeriesDataSource.getAllSeries(category) } returns series

            // When
            val result = repository.getSeries(category).getOrNull()

            // Then
            assertEquals(series, result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
                localSeriesDataSource.getAllSeries(category)
            }
            confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
        }

    @Test
    fun `give UnknownHostException, when getSeries is called, then result Success`() = runBlocking {
        // Given
        val error: UnknownHostException = mockk()
        val category = "action"
        val series: List<Series> = mockk()

        coEvery { remoteSeriesDataSource.getSeries(category) } throws error
        coEvery { localSeriesDataSource.getAllSeries(category) } returns series

        // When
        val result = repository.getSeries(category).getOrNull()

        // Then
        assertEquals(series, result)
        coVerify {
            remoteSeriesDataSource.getSeries(category)
            localSeriesDataSource.getAllSeries(category)
        }
        confirmVerified(remoteSeriesDataSource, localSeriesDataSource)
    }

    @Test
    fun `give Exception, when getSeries is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val error: Exception = mockk()
            val category = "action"

            coEvery { remoteSeriesDataSource.getSeries(category) } throws error

            // When
            val result = repository.getSeries(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                remoteSeriesDataSource.getSeries(category)
            }
            confirmVerified(remoteSeriesDataSource)
        }
}
