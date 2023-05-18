package com.example.instaflix.domain.usecase

import com.example.instaflix.BaseTest
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

class GetFilmsByIdUCTest : BaseTest() {

    @MockK
    lateinit var filmRepository: FilmRepository

    private lateinit var useCase: GetFilmsByIdUC

    override fun setup() {
        super.setup()
        useCase = GetFilmsByIdUC(filmRepository)
    }

    @Test
    fun `when invoke is called, then return success result`() = runBlocking {
        // Given
        val id = 1L
        val film: Film = mockk()

        coEvery { filmRepository.getFilmById(id) } returns Result.success(film)

        // When
        val resultFlow = useCase.invoke(id)
        val result = resultFlow.getOrNull()

        // Then
        assertEquals(film, result)
        coVerify {
            filmRepository.getFilmById(id)
        }
        confirmVerified(filmRepository)
    }

    @Test
    fun `when invoke is called, then return error result`() = runBlocking {
        // Given
        val id = 1L
        val error = Exception("Film not found")

        coEvery { filmRepository.getFilmById(id) } returns Result.failure(error)

        // When
        val result = useCase.invoke(id).exceptionOrNull()

        // Then
        assertEquals(error, result)
        coVerify {
            filmRepository.getFilmById(id)
        }
        confirmVerified(filmRepository)
    }
}
