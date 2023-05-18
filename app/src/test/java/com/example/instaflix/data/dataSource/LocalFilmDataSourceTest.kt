package com.example.instaflix.data.dataSource

import com.example.instaflix.BaseTest
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.data.mapper.mapToFilm
import com.example.instaflix.data.mapper.mapToFilmEntity
import com.example.instaflix.data.mapper.mapToFilms
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.model.Film
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

class LocalFilmDataSourceTest : BaseTest() {

    @MockK
    lateinit var filmDao: FilmDao

    private lateinit var dataSource: LocalFilmDataSource

    @Before
    override fun setup() {
        super.setup()
        dataSource = LocalFilmDataSource(
            filmDao = filmDao,
        )
    }

    @Test
    fun `give category, when getAllFilms is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val films: List<FilmEntity> = listOf(mockk())
        val expectedResults: List<Film> = listOf(mockk())

        mockkStatic(films::mapToFilms)

        every { filmDao.getAllFilms(category) } returns films
        every { films.mapToFilms() } returns expectedResults

        // When
        val result = dataSource.getAllFilms(category)

        // Then
        assertEquals(expectedResults, result)
        verify {
            filmDao.getAllFilms(category)
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

        mockkStatic(filmEntity::mapToFilm)
        every { filmDao.getFilmById(filmId) } returns filmEntity
        every { filmEntity.mapToFilm() } returns expectedFilm

        // When
        val resultFlow = dataSource.getFilmByID(filmId)

        // Then
        assertEquals(expectedFilm, resultFlow)
        verify {
            filmDao.getFilmById(filmId)
            filmEntity.mapToFilm()
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
        dataSource.insertFilms(films, category)

        // Then
        verify {
            filmDao.insertFilms(expectedFilmEntity)
            films.mapToFilmEntity(category)
        }
        confirmVerified(filmDao)
    }
}
