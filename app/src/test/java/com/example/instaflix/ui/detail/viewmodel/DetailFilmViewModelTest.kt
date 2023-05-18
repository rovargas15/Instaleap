package com.example.instaflix.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.instaflix.BaseTest
import com.example.instaflix.DispatcherRule
import com.example.instaflix.domain.exception.UnknowException
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import com.example.instaflix.ui.utils.Parameter
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailFilmViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = DispatcherRule()

    @MockK
    lateinit var getFilmsByIdUC: GetFilmsByIdUC

    @MockK
    lateinit var getSeriesByIdUC: GetSeriesByIdUC

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DetailFilmViewModel

    override fun setup() {
        super.setup()
        viewModel = DetailFilmViewModel(
            getFilmsByIdUC = getFilmsByIdUC,
            getSeriesByIdUC = getSeriesByIdUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher,
            savedStateHandle = savedStateHandle,
        )
    }

    @Test
    fun `give FILM_ID is 1 when invoke is called, then return film`() {
        // Give
        val film: Film = mockk()
        every { savedStateHandle.get<String>(Parameter.SERIES_ID) } returns ""
        every { savedStateHandle.get<String>(Parameter.FILM_ID) } returns "1"
        every { getFilmsByIdUC.invoke(1) } answers { Result.success(film) }

        // When
        viewModel.onLoad()

        // Then
        val state = viewModel.filmState

        assert(state.isError().not())
        assertEquals(state.film, film)
        verify() {
            getFilmsByIdUC.invoke(1)
            savedStateHandle.get<String>(Parameter.FILM_ID)
            savedStateHandle.get<String>(Parameter.SERIES_ID)
        }
        confirmVerified(getFilmsByIdUC, savedStateHandle)
    }

    @Test
    fun `give SERIES_ID is 1 when invoke is called, then return series`() {
        // Give
        val series: Series = mockk()
        every { savedStateHandle.get<String>(Parameter.FILM_ID) } returns ""
        every { savedStateHandle.get<String>(Parameter.SERIES_ID) } returns "1"
        every { getSeriesByIdUC.invoke(1) } answers { Result.success(series) }

        // When
        viewModel.onLoad()

        // Then
        val state = viewModel.seriesState

        assert(state.isError().not())
        assertEquals(state.series, series)
        verify() {
            getSeriesByIdUC.invoke(1)
            savedStateHandle.get<String>(Parameter.FILM_ID)
            savedStateHandle.get<String>(Parameter.SERIES_ID)
        }
        confirmVerified(getFilmsByIdUC, savedStateHandle)
    }

    @Test
    fun `give FILM_ID is 1 when invoke is called, then return error`() {
        // Give
        every { savedStateHandle.get<String>(Parameter.SERIES_ID) } returns ""
        every { savedStateHandle.get<String>(Parameter.FILM_ID) } returns "1"
        every { getFilmsByIdUC.invoke(1) } answers { Result.failure(UnknowException("Error test")) }

        // When
        viewModel.onLoad()

        // Then
        val state = viewModel.filmState

        assert(state.isError())
        assertEquals(state.getErrorMessage(), "Error test")
        verify() {
            getFilmsByIdUC.invoke(1)
            savedStateHandle.get<String>(Parameter.FILM_ID)
            savedStateHandle.get<String>(Parameter.SERIES_ID)
        }
        confirmVerified(getFilmsByIdUC, savedStateHandle)
    }

    @Test
    fun `give SERIES_ID is 1 when invoke is called, then return error`() {
        // Give
        every { savedStateHandle.get<String>(Parameter.FILM_ID) } returns ""
        every { savedStateHandle.get<String>(Parameter.SERIES_ID) } returns "1"
        every { getSeriesByIdUC.invoke(1) } answers { Result.failure(UnknowException("Error test")) }

        // When
        viewModel.onLoad()

        // Then
        val state = viewModel.seriesState

        assert(state.isError())
        assertEquals(state.getErrorMessage(), "Error test")
        verify() {
            getSeriesByIdUC.invoke(1)
            savedStateHandle.get<String>(Parameter.FILM_ID)
            savedStateHandle.get<String>(Parameter.SERIES_ID)
        }
        confirmVerified(getFilmsByIdUC, savedStateHandle)
    }
}
