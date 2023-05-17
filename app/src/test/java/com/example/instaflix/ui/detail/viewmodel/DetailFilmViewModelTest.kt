package com.example.instaflix.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.instaflix.BaseTest
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher

class DetailFilmViewModelTest : BaseTest() {

    @MockK
    lateinit var getFilmsByIdUC: GetFilmsByIdUC

    @MockK
    lateinit var getSeriesByIdUC: GetSeriesByIdUC

    @MockK
    lateinit var coroutineDispatcher: CoroutineDispatcher

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DetailFilmViewModel

    override fun setup() {
        super.setup()
        viewModel = DetailFilmViewModel(
            getFilmsByIdUC,
            getSeriesByIdUC,
            coroutineDispatcher,
            savedStateHandle,
        )
    }
}
