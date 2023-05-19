package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.dataSource.LocalFilmDataSource
import com.example.instaflix.data.dataSource.RemoteFilmDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.exception.PermissionDeniedException
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
import java.net.HttpURLConnection

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
        val films: List<Film> = mockk()
        val category = "action"

        coEvery { localFilmDataSource.getAllFilms(category) } returns flowOf(films)

        // When
        val result = repository.getFilms(category).single().getOrNull()

        // Then
        assertEquals(films, result)
        coVerify {
            localFilmDataSource.getAllFilms(category)
        }
        confirmVerified(localFilmDataSource)
    }

    @Test
    fun `give category, when updateSeries is called, then return success result`() = runBlocking {
        // Given
        val baseResponse: BaseResponse<FilmResponse> = mockk()
        val seriesResponse: List<FilmResponse> = mockk()
        val category = "action"

        mockkStatic(baseResponse::mapToBaseResult)

        every { baseResponse.results } returns seriesResponse
        coEvery { remoteFilmDataSource.getFilms(category) } returns baseResponse
        coEvery {
            localFilmDataSource.insertFilms(seriesResponse, category)
        } just runs

        // When
        val result = repository.updateFilm(category)

        // Then
        assertEquals(true, result.isSuccess)
        coVerify {
            remoteFilmDataSource.getFilms(category)
            localFilmDataSource.insertFilms(seriesResponse, category)
        }
        verify {
            baseResponse.results
        }
        confirmVerified(remoteFilmDataSource, localFilmDataSource)
    }

    @Test
    fun `give id, when getFilmsById is called, then return success result`() = runBlocking {
        // Given
        val id = 1L
        val films: Film = mockk()

        coEvery {
            localFilmDataSource.getFilmByID(id)
        } returns films

        // When
        val result = repository.getFilmById(id).getOrNull()

        // Then
        assertEquals(films, result)
        coVerify {
            localFilmDataSource.getFilmByID(id)
        }
        confirmVerified(localFilmDataSource)
    }

    @Test
    fun `give HttpException HTTP_UNAUTHORIZED, when updateSeries is called, then return failed result PermissionDeniedException`() =
        runBlocking {
            // Given
            val error: HttpException = mockk()
            val category = "action"

            every { error.code() } returns HttpURLConnection.HTTP_UNAUTHORIZED
            coEvery { remoteFilmDataSource.getFilms(category) } throws error

            // When
            val result = repository.updateFilm(category).exceptionOrNull()

            // Then
            assertEquals(PermissionDeniedException(), result)
            coVerify {
                remoteFilmDataSource.getFilms(category)
            }
            confirmVerified(remoteFilmDataSource)
        }
}
