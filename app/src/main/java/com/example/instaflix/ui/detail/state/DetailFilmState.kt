package com.example.instaflix.ui.detail.state

import com.example.instaflix.domain.model.Film

sealed interface DetailFilmState {
    object Loader : DetailFilmState
    object Error : DetailFilmState
    data class Success(val film: Film) : DetailFilmState
}
