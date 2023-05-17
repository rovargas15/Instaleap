package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetLocalFilmsByCategoryUCTest : BaseTest() {

    @MockK
    lateinit var filmRepository: FilmRepository

    private lateinit var useCase: GetLocalFilmsByCategoryUC

    override fun setup() {
        super.setup()
        useCase = GetLocalFilmsByCategoryUC(filmRepository)
    }

    @Test
    fun `when invoke is called, then return success result`() = runBlocking {
        // Given
        val category = "action"
        val films: List<Film> = mockk()

        coEvery { filmRepository.getLocalFilms(category) } returns flowOf(Result.success(films))

        // When
        val resultFlow = useCase.invoke(category)
        val result = resultFlow.single()

        // Then
        TestCase.assertEquals(Result.success(films), result)
        coVerify {
            filmRepository.getLocalFilms(category)
        }
        confirmVerified(filmRepository)
    }

    @Test
    fun `when invoke is called, then return error result`() = runBlocking {
        // Given
        val category = "action"
        val error = Exception("Film not found")

        coEvery { filmRepository.getLocalFilms(category) } returns flowOf(Result.failure(error))

        // When
        val resultFlow = useCase.invoke(category)
        val result = resultFlow.single()

        // Then
        TestCase.assertEquals(Result.failure<Film>(error), result)
        coVerify {
            filmRepository.getLocalFilms(category)
        }
        confirmVerified(filmRepository)
    }
}
