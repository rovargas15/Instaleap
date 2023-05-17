package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFilmsByCategoryUCTest : BaseTest() {

    @MockK
    lateinit var filmRepository: FilmRepository

    private lateinit var useCase: GetFilmsByCategoryUC

    override fun setup() {
        super.setup()
        useCase = GetFilmsByCategoryUC(filmRepository)
    }

    @Test
    fun `when invoke is called and repository returns success, then return success result`() =
        runBlocking {
            // Given
            val category = "action"
            val response: BaseResult<Film> = mockk()

            coEvery { filmRepository.getFilms(category) } returns Result.success(response)

            // When
            val result = useCase.invoke(category)

            // Then
            assertEquals(Result.success(response), result)
            coVerify {
                filmRepository.getFilms(category)
            }
            confirmVerified(filmRepository)
        }

    @Test
    fun `when invoke is called and repository returns error, then return error result`() =
        runBlocking {
            // Given
            val category = "action"
            val error = UnknowException()

            coEvery { filmRepository.getFilms(category) } returns Result.failure(error)

            // When
            val result = useCase.invoke(category)

            // Then
            assertEquals(Result.failure<BaseResult<Film>>(error), result)
            coVerify {
                filmRepository.getFilms(category)
            }
            confirmVerified(filmRepository)
        }
}
