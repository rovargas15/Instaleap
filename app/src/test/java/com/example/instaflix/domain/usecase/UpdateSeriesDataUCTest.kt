package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateSeriesDataUCTest : BaseTest() {

    @MockK
    lateinit var repository: SeriesRepository

    private lateinit var useCase: UpdateSeriesDataUC

    override fun setup() {
        super.setup()
        useCase = UpdateSeriesDataUC(repository)
    }

    @Test
    fun `when invoke is called and repository returns success, then return success result`() =
        runBlocking {
            // Given
            val category = "action"

            coEvery { repository.updateSeries(category) } returns Result.success(Unit)

            // When
            val result = useCase.invoke(category).isSuccess

            // Then
            TestCase.assertEquals(true, result)
            coVerify {
                repository.updateSeries(category)
            }
            confirmVerified(repository)
        }

    @Test
    fun `when invoke is called and repository returns error, then return error result`() =
        runBlocking {
            // Given
            val category = "action"
            val error = UnknowException()

            coEvery { repository.updateSeries(category) } returns Result.failure(error)

            // When
            val result = useCase.invoke(category).exceptionOrNull()

            // Then
            TestCase.assertEquals(error, result)
            coVerify {
                repository.updateSeries(category)
            }
            confirmVerified(repository)
        }
}
