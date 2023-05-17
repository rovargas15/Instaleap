package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
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
            val response: BaseResult<Series> = mockk()

            coEvery { seriesRepository.getSeries(category) } returns Result.success(response)

            // When
            val result = useCase.invoke(category)

            // Then
            TestCase.assertEquals(Result.success(response), result)
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

            coEvery { seriesRepository.getSeries(category) } returns Result.failure(error)

            // When
            val result = useCase.invoke(category)

            // Then
            TestCase.assertEquals(Result.failure<BaseResult<Film>>(error), result)
            coVerify {
                seriesRepository.getSeries(category)
            }
            confirmVerified(seriesRepository)
        }
}
