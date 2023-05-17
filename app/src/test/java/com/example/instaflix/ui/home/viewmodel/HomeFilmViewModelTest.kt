package com.example.instaflix.ui.home.viewmodel

import com.example.instaflix.BaseTest
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher

class HomeFilmViewModelTest : BaseTest() {

    @MockK
    lateinit var getFilmsByCategoryUC: GetFilmsByCategoryUC

    @MockK
    lateinit var getSeriesByCategoryUC: GetSeriesByCategoryUC

    @MockK
    lateinit var getLocalFilmsByCategoryUC: GetLocalFilmsByCategoryUC

    @MockK
    lateinit var coroutineDispatcher: CoroutineDispatcher

    private lateinit var viewModel: HomeFilmViewModel

    override fun setup() {
        super.setup()
        viewModel = HomeFilmViewModel(
            getFilmsByCategoryUC = getFilmsByCategoryUC,
            getSeriesByCategoryUC = getSeriesByCategoryUC,
            getLocalFilmsByCategoryUC = getLocalFilmsByCategoryUC,
            coroutineDispatcher = coroutineDispatcher,
        )
    }
}
