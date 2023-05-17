package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.mapper.mapToFilmEntity
import com.example.instaflix.data.mapper.mapToFilms
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.exception.InternetException
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
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
    lateinit var api: FilmApi

    @MockK
    lateinit var filmDao: FilmDao

    private lateinit var repository: FilmRepository

    @Before
    override fun setup() {
        super.setup()
        repository = FilmRepositoryImpl(
            api = api,
            filmDao = filmDao,
        )
    }

    @Test
    fun `give category, when getFilms is called, then return success result`() = runBlocking {
        // Given
        val filmResponse: List<FilmResponse> = mockk()
        val baseResponse: BaseResponse<FilmResponse> = mockk()
        val category = "action"
        val expectedFilmsEntity: List<FilmEntity> = listOf(mockk())
        val expectedBaseResult: BaseResult<Film> = mockk()

        mockkStatic(baseResponse::mapToBaseResult)
        mockkStatic(filmResponse::mapToFilmEntity)

        coEvery { api.getFilms(category) } returns baseResponse
        coEvery { filmDao.insertFilms(expectedFilmsEntity) } returns Unit

        every { baseResponse.results } returns filmResponse
        every { baseResponse.mapToBaseResult() } returns expectedBaseResult
        every { filmResponse.mapToFilmEntity(category) } returns expectedFilmsEntity

        // When
        val result = repository.getFilms(category)

        // Then
        assertEquals(Result.success(expectedBaseResult), result)
        coVerify {
            api.getFilms(category)
            filmDao.insertFilms(expectedFilmsEntity)
        }
        verify {
            baseResponse.results
            baseResponse.mapToBaseResult()
            filmResponse.mapToFilmEntity(category)
        }
        confirmVerified(api, filmDao)
    }

    @Test
    fun `give HttpException HTTP_UNAUTHORIZED, when getFilms is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED
            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give Exception, when insertFilms is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val filmResponse: List<FilmResponse> = mockk()
            val baseResponse: BaseResponse<FilmResponse> = mockk()
            val category = "action"
            val expectedFilmsEntity: List<FilmEntity> = listOf(mockk())

            mockkStatic(baseResponse::mapToBaseResult)

            coEvery { api.getFilms(category) } returns baseResponse
            coEvery { filmDao.insertFilms(expectedFilmsEntity) } throws Throwable()
            every { baseResponse.results } returns filmResponse

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api, filmDao)
        }

    @Test
    fun `give HttpException HTTP_FORBIDDEN, when getFilms is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_FORBIDDEN
            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(PermissionDeniedException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give SocketTimeoutException, when getFilms is called, then return failed result InternetException`() =
        runBlocking {
            // Given
            val error: SocketTimeoutException = mockk()
            val category = "action"

            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(InternetException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give ConnectException, when getFilms is called, then return failed result InternetException`() =
        runBlocking {
            // Given
            val error: ConnectException = mockk()
            val category = "action"

            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(InternetException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give UnknownHostException, when getFilms is called, then return failed result InternetException`() =
        runBlocking {
            // Given
            val error: UnknownHostException = mockk()
            val category = "action"

            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(InternetException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give Exception, when getFilms is called, then return failed result UnknowException`() =
        runBlocking {
            // Given
            val error: Exception = mockk()
            val category = "action"

            coEvery { api.getFilms(category) } throws error

            // When
            val result = repository.getFilms(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(UnknowException()), result)
            coVerify {
                api.getFilms(category)
            }
            confirmVerified(api)
        }

    @Test
    fun `give category, when getLocalFilms is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val films: List<FilmEntity> = listOf(mockk())
        val expectedResults: List<Film> = listOf(mockk())

        mockkStatic(films::mapToFilms)

        every { filmDao.getAll(category) } returns flowOf(films)
        every { films.mapToFilms() } returns expectedResults

        // When
        val resultFlow = repository.getLocalFilms(category).single()

        // Then
        assertEquals(Result.success(expectedResults), resultFlow)
        verify {
            filmDao.getAll(category)
            films.mapToFilms()
        }
        confirmVerified(filmDao)
    }

    @Test
    fun `give filmId, when getFilmById is called, then return success result`() = runBlocking {
        // Given
        val filmId = 1L
        val filmEntity: FilmEntity = mockk()
        val expectedFilm: Film = mockk()

        mockkStatic(filmEntity::mapToBaseResult)
        every { filmDao.getFilmById(filmId) } returns flowOf(filmEntity)
        every { filmEntity.mapToBaseResult() } returns expectedFilm

        // When
        val resultFlow = repository.getFilmById(filmId).single()

        // Then
        assertEquals(Result.success(expectedFilm), resultFlow)
        verify {
            filmDao.getFilmById(filmId)
            filmEntity.mapToBaseResult()
        }
        confirmVerified(filmDao)
    }

    @Test
    fun `when insertFilms is called, then call filmDao insertFilms`() = runBlocking {
        // Given
        val films: List<FilmResponse> = mockk()
        val expectedFilmEntity: List<FilmEntity> = mockk()
        val category = "action"

        mockkStatic(films::mapToFilmEntity)
        every { films.mapToFilmEntity(category) } returns expectedFilmEntity
        every { filmDao.insertFilms(expectedFilmEntity) } just runs

        // When
        repository.insertFilms(films, category)

        // Then
        verify {
            filmDao.insertFilms(expectedFilmEntity)
            films.mapToFilmEntity(category)
        }
        confirmVerified(filmDao)
    }
}
