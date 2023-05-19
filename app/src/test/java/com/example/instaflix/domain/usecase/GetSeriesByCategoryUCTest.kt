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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetSeriesByCategoryUCTest : BaseTest() {

    @MockK
    lateinit var seriesRepository: SeriesRepository

    private lateinit var useCase: GetSeriesByCategoryUC

    override fun setup() {
        super.setup()
        useCase = GetSeriesByCategoryUC(seriesRepository)
    }

    @Test
    fun `when invoke is called and repository returns success, then return success result`() =
        runBlocking {
            // Given
            val category = "action"
            val response: List<Series> = mockk()

            coEvery { seriesRepository.getSeries(category) } returns flowOf(Result.success(response))

            // When
            val result = useCase.invoke(category).single().getOrNull()

            // Then
            assertEquals(response, result)
            coVerify {
                seriesRepository.getSeries(category)
            }
            confirmVerified(seriesRepository)
        }

    @Test
    fun `when invoke is called and repository returns error, then return error result`() =
        runBlocking {
            // Given
            val category = "action"
            val error = UnknowException()

            coEvery { seriesRepository.getSeries(category) } returns flowOf(Result.failure(error))

            // When
            val result = useCase.invoke(category).single().exceptionOrNull()

            // Then
            assertEquals(error, result)
            coVerify {
                seriesRepository.getSeries(category)
            }
            confirmVerified(seriesRepository)
        }
}
