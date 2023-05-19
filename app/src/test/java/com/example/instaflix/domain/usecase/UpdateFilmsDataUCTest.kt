package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.repository.FilmRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateFilmsDataUCTest : BaseTest() {
    @MockK
    lateinit var filmRepository: FilmRepository

    private lateinit var useCase: UpdateFilmsDataUC

    override fun setup() {
        super.setup()
        useCase = UpdateFilmsDataUC(filmRepository)
    }

    @Test
    fun `when invoke is called and repository returns success, then return success result`() =
        runBlocking {
            // Given
            val category = "action"

            coEvery { filmRepository.updateFilm(category) } returns Result.success(Unit)

            // When
            val result = useCase.invoke(category).isSuccess

            // Then
            assertEquals(true, result)
            coVerify {
                filmRepository.updateFilm(category)
            }
            confirmVerified(filmRepository)
        }

    @Test
    fun `when invoke is called and repository returns error, then return error result`() =
        runBlocking {
            // Given
            val category = "action"
            val error = UnknowException()

            coEvery { filmRepository.updateFilm(category) } returns Result.failure(error)

            // When
            val result = useCase.invoke(category).exceptionOrNull()

            // Then
            assertEquals(error, result)
            coVerify {
                filmRepository.updateFilm(category)
            }
            confirmVerified(filmRepository)
        }
}
