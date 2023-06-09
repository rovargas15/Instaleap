package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetSeriesByIdUCTest : BaseTest() {

    @MockK
    lateinit var seriesRepository: SeriesRepository

    private lateinit var useCase: GetSeriesByIdUC

    override fun setup() {
        super.setup()
        useCase = GetSeriesByIdUC(seriesRepository)
    }

    @Test
    fun `when invoke is called and repository returns success, then return success result`() =
        runBlocking {
            // Given
            val id = 1L
            val series: Series = mockk()

            coEvery { seriesRepository.getSeriesById(id) } returns Result.success(series)

            // When
            val result = useCase.invoke(id).getOrNull()

            // Then
            assertEquals(series, result)
            coVerify {
                seriesRepository.getSeriesById(id)
            }
            confirmVerified(seriesRepository)
        }

    @Test
    fun `when invoke is called and repository returns error, then return error result`() =
        runBlocking {
            // Given
            val id = 1L
            val error = UnknowException()

            coEvery { seriesRepository.getSeriesById(id) } returns Result.failure(error)

            // When
            val result = useCase.invoke(id).exceptionOrNull()

            // Then
            assertEquals(error, result)
            coVerify {
                seriesRepository.getSeriesById(id)
            }
            confirmVerified(seriesRepository)
        }
}
