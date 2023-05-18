package com.example.instaflix.data.dataSource

import com.example.instaflix.BaseTest
import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.local.dto.SeriesEntity
import com.example.instaflix.data.mapper.mapToSeries
import com.example.instaflix.data.mapper.mapToSeriesEntity
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.model.Series
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

class LocalSeriesDataSourceTest : BaseTest() {

    @MockK
    lateinit var seriesDao: SeriesDao

    private lateinit var dataSource: LocalSeriesDataSource

    @Before
    override fun setup() {
        super.setup()
        dataSource = LocalSeriesDataSource(
            seriesDao = seriesDao,
        )
    }

    @Test
    fun `give category, when getAllSeries is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val series: SeriesEntity = mockk()
        val expectedResults: Series = mockk()

        mockkStatic(series::mapToSeries)

        every { seriesDao.getAllSeries(category) } returns listOf(series)
        every { series.mapToSeries() } returns expectedResults

        // When
        val result = dataSource.getAllSeries(category)

        // Then
        assertEquals(listOf(expectedResults), result)
        verify {
            seriesDao.getAllSeries(category)
            series.mapToSeries()
        }
        confirmVerified(seriesDao)
    }

    @Test
    fun `give filmId, when getFilmById is called, then return success result`() = runBlocking {
        // Given
        val filmId = 1L
        val filmEntity: SeriesEntity = mockk()
        val expectedFilm: Series = mockk()

        mockkStatic(filmEntity::mapToSeries)
        every { seriesDao.getSeriesById(filmId) } returns filmEntity
        every { filmEntity.mapToSeries() } returns expectedFilm

        // When
        val result = dataSource.getSeriesById(filmId)

        // Then
        assertEquals(expectedFilm, result)
        verify {
            seriesDao.getSeriesById(filmId)
            filmEntity.mapToSeries()
        }
        confirmVerified(seriesDao)
    }

    @Test
    fun `when insertFilms is called, then call seriesDao insertFilms`() = runBlocking {
        // Given
        val series: SeriesResponse = mockk()
        val expectedSeriesEntity: SeriesEntity = mockk()
        val category = "action"

        mockkStatic(series::mapToSeriesEntity)
        every { series.mapToSeriesEntity(category) } returns expectedSeriesEntity
        every { seriesDao.insertSeries(listOf(expectedSeriesEntity)) } just runs

        // When
        dataSource.insertSeries(listOf(series), category)

        // Then
        verify {
            seriesDao.insertSeries(listOf(expectedSeriesEntity))
            series.mapToSeriesEntity(category)
        }
        confirmVerified(seriesDao)
    }
}
