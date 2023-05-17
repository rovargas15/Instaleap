package com.example.instaflix.data.repository

import com.example.instaflix.BaseTest
import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.local.dto.SeriesEntity
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.mapper.mapToSeries
import com.example.instaflix.data.mapper.mapToSeriesEntity
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.BaseResult
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

class SeriesRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var api: SeriesApi

    @MockK
    lateinit var seriesDao: SeriesDao

    private lateinit var repository: SeriesRepository

    @Before
    override fun setup() {
        super.setup()
        repository = SeriesRepositoryImpl(
            api = api,
            seriesDao = seriesDao,
        )
    }

    @Test
    fun `give category, when getSeries is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val seriesResponse: SeriesResponse = mockk()
        val baseResponse: BaseResponse<SeriesResponse> = mockk()
        val expectedSeriesEntity: SeriesEntity = mockk()
        val expectedBaseResult: BaseResult<Series> = mockk()

        mockkStatic(baseResponse::mapToBaseResult)
        mockkStatic(seriesResponse::mapToSeries)

        coEvery { api.getSeries(category) } returns baseResponse
        coEvery { seriesDao.insertSeries(listOf(expectedSeriesEntity)) } returns Unit

        every { baseResponse.results } returns listOf(seriesResponse)
        every { baseResponse.mapToBaseResult() } returns expectedBaseResult
        every { seriesResponse.mapToSeriesEntity(category) } returns expectedSeriesEntity

        // When
        val result = repository.getSeries(category)

        // Then
        assertEquals(Result.success(expectedBaseResult), result)
        coVerify {
            api.getSeries(category)
            seriesDao.insertSeries(listOf(expectedSeriesEntity))
        }
        verify {
            baseResponse.results
            baseResponse.mapToBaseResult()
            seriesResponse.mapToSeriesEntity(category)
        }
        confirmVerified(api, seriesDao)
    }

    @Test
    fun `give exception, when getSeries is called, then return failed`() = runBlocking {
        // Given
        val category = "action"
        val seriesResponse: SeriesResponse = mockk()
        val baseResponse: BaseResponse<SeriesResponse> = mockk()
        val expectedSeriesEntity: SeriesEntity = mockk()

        mockkStatic(baseResponse::mapToBaseResult)
        mockkStatic(seriesResponse::mapToSeries)

        coEvery { api.getSeries(category) } returns baseResponse

        every { baseResponse.results } returns listOf(seriesResponse)
        every { seriesResponse.mapToSeriesEntity(category) } returns expectedSeriesEntity

        // When
        val result = repository.getSeries(category)

        // Then
        assertEquals(Result.failure<BaseResult<Series>>(UnknowException()), result)
        coVerify {
            api.getSeries(category)
            seriesDao.insertSeries(listOf(expectedSeriesEntity))
        }
        verify {
            baseResponse.results
            seriesResponse.mapToSeriesEntity(category)
        }
        confirmVerified(api, seriesDao)
    }

    @Test
    fun `give category, when getLocalSeries is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val seriesEntity: SeriesEntity = mockk()
        val expectedResults: Series = mockk()

        mockkStatic(seriesEntity::mapToSeries)

        every { seriesDao.getAllSeries(category) } returns flowOf(listOf(seriesEntity))
        every { seriesEntity.mapToSeries() } returns expectedResults

        // When
        val resultFlow = repository.getLocalSeries(category).single()

        // Then
        assertEquals(Result.success(listOf(expectedResults)), resultFlow)
        verify {
            seriesDao.getAllSeries(category)
            seriesEntity.mapToSeries()
        }
        confirmVerified(seriesDao)
    }

    @Test
    fun `give seriesId, when getSeriesById is called, then return success result`() = runBlocking {
        // Given
        val seriesId = 1L
        val seriesEntity: SeriesEntity = mockk()
        val expectedSeries: Series = mockk()

        mockkStatic(seriesEntity::mapToSeries)
        every { seriesDao.getSeriesById(seriesId) } returns flowOf(seriesEntity)
        every { seriesEntity.mapToSeries() } returns expectedSeries

        // When
        val resultFlow = repository.getSeriesById(seriesId).single()

        // Then
        assertEquals(Result.success(expectedSeries), resultFlow)
        verify {
            seriesDao.getSeriesById(seriesId)
            seriesEntity.mapToSeries()
        }
        confirmVerified(seriesDao)
    }

    @Test
    fun `when insertSeries is called, then call seriesDao insertSeries`() = runBlocking {
        // Given
        val category = "action"
        val seriesResponse: SeriesResponse = mockk()
        val expectedSeriesEntity: SeriesEntity = mockk()

        mockkStatic(seriesResponse::mapToSeriesEntity)
        every { seriesResponse.mapToSeriesEntity(category) } returns expectedSeriesEntity
        every { seriesDao.insertSeries(listOf(expectedSeriesEntity)) } just runs

        // When
        repository.insertSeries(listOf(seriesResponse), category)

        // Then
        verify {
            seriesDao.insertSeries(listOf(expectedSeriesEntity))
            seriesResponse.mapToSeriesEntity(category)
        }
        confirmVerified(seriesDao)
    }
}
