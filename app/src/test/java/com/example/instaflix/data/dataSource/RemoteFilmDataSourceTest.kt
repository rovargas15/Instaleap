package com.example.instaflix.data.dataSource

import com.example.instaflix.BaseTest
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteFilmDataSourceTest : BaseTest() {

    @MockK
    lateinit var api: FilmApi

    private lateinit var dataSource: RemoteFilmDataSource

    @Before
    override fun setup() {
        super.setup()
        dataSource = RemoteFilmDataSource(
            api = api,
        )
    }

    @Test
    fun `getFilms should return films from API`() = runBlocking {
        // Given
        val category = "action"
        val films: BaseResponse<FilmResponse> = mockk()
        coEvery { api.getFilms(category) } returns films

        // When
        val result = dataSource.getFilms(category)

        // Then
        assertEquals(films, result)
        coVerify { api.getFilms(category) }
        confirmVerified(api)
    }
}
