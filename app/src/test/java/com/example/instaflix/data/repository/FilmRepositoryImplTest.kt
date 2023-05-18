package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.dataSource.LocalFilmDataSource
import com.example.instaflix.data.dataSource.RemoteFilmDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.exception.PermissionDeniedException
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
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

class FilmRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var localFilmDataSource: LocalFilmDataSource

    @MockK
    lateinit var remoteFilmDataSource: RemoteFilmDataSource

    private lateinit var repository: FilmRepository

    @Before
    override fun setup() {
        super.setup()
        repository = FilmRepositoryImpl(
            localFilmDataSource = localFilmDataSource,
            remoteFilmDataSource = remoteFilmDataSource,
        )
    }

    @Test
    fun `give category, when getFilms is called, then return success result`() = runBlocking {
        // Given
        val baseResponse: BaseResponse<FilmResponse> = mockk()
        val expectedBaseResult: BaseResult<Film> = mockk()
        val seriesResponse: List<FilmResponse> = mockk()
        val expectedSeries: List<Film> = mockk()
        val category = "action"

        mockkStatic(baseResponse::mapToBaseResult)

        every { baseResponse.results } returns seriesResponse
        every { baseResponse.mapToBaseResult() } returns expectedBaseResult
        every { expectedBaseResult.results } returns expectedSeries

        coEvery { remoteFilmDataSource.getFilms(category) } returns baseResponse
        coEvery {
            localFilmDataSource.insertFilms(seriesResponse, category)
        } just runs

        // When
        val result = repository.getFilms(category).getOrNull()

        // Then
        assertEquals(expectedSeries, result)
        coVerify {
            remoteFilmDataSource.getFilms(category)
            localFilmDataSource.insertFilms(seriesResponse, category)
        }
        verify {
            baseResponse.results
            baseResponse.mapToBaseResult()
            expectedBaseResult.results
        }
        confirmVerified(remoteFilmDataSource, localFilmDataSource)
    }

    @Test
    fun `give HttpException HTTP_UNAUTHORIZED, when getFilms is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED
            coEvery { remoteFilmDataSource.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
            }
            confirmVerified(remoteFilmDataSource)
        }

    @Test
    fun `give Exception, when insertFilms is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val filmResponse: List<FilmResponse> = mockk()
            val baseResponse: BaseResponse<FilmResponse> = mockk()
            val category = "action"

            mockkStatic(baseResponse::mapToBaseResult)

            coEvery { remoteFilmDataSource.getFilms(category) } returns baseResponse
            coEvery { localFilmDataSource.insertFilms(filmResponse, category) } throws Throwable()
            every { baseResponse.results } returns filmResponse

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
                localFilmDataSource.insertFilms(filmResponse, category)
            }
            confirmVerified(remoteFilmDataSource, localFilmDataSource)
        }

    @Test
    fun `give HttpException HTTP_FORBIDDEN, when getFilms is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_FORBIDDEN
            coEvery { remoteFilmDataSource.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
            }
            confirmVerified(remoteFilmDataSource)
        }

    @Test
    fun `give SocketTimeoutException, when getFilms is called, then result Success`() =
        runBlocking {
            // Given
            val error: SocketTimeoutException = mockk()
            val category = "action"
            val films: List<Film> = mockk()

            coEvery { remoteFilmDataSource.getFilms(category) } throws error
            coEvery { localFilmDataSource.getAllFilms(category) } returns films

            // When
            val result = repository.getFilms(category).getOrNull()

            // Then
            assertEquals(films, result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
                localFilmDataSource.getAllFilms(category)
            }
            confirmVerified(remoteFilmDataSource, localFilmDataSource)
        }

    @Test
    fun `give ConnectException, when getFilms is called, then return result Success`() =
        runBlocking {
            // Given
            val error: ConnectException = mockk()
            val category = "action"
            val films: List<Film> = mockk()

            coEvery { remoteFilmDataSource.getFilms(category) } throws error
            coEvery { localFilmDataSource.getAllFilms(category) } returns films

            // When
            val result = repository.getFilms(category).getOrNull()

            // Then
            assertEquals(films, result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
                localFilmDataSource.getAllFilms(category)
            }
            confirmVerified(remoteFilmDataSource, localFilmDataSource)
        }

    @Test
    fun `give UnknownHostException, when getFilms is called, then result Success`() = runBlocking {
        // Given
        val error: UnknownHostException = mockk()
        val category = "action"
        val films: List<Film> = mockk()

        coEvery { remoteFilmDataSource.getFilms(category) } throws error
        coEvery { localFilmDataSource.getAllFilms(category) } returns films

        // When
        val result = repository.getFilms(category).getOrNull()

        // Then
        assertEquals(films, result)
        coVerify {
            remoteFilmDataSource.getFilms(category)
            localFilmDataSource.getAllFilms(category)
        }
        confirmVerified(remoteFilmDataSource, localFilmDataSource)
    }

    @Test
    fun `give Exception, when getFilms is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val error: Exception = mockk()
            val category = "action"

            coEvery { remoteFilmDataSource.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
            }
            confirmVerified(remoteFilmDataSource)
        }
}
