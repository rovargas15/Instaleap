package com.example.instaflix.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.instaflix.domain.usecase.GetFilmsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailFilmViewModel @Inject constructor(
    private val getMovieByIdUC: GetFilmsUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : ViewModel()
