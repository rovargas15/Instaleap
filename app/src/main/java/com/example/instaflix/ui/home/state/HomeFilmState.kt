package com.example.instaflix.ui.home.state

import com.example.instaflix.domain.model.Film

sealed interface HomeFilmState {
    object Loader : HomeFilmState
    object Error : HomeFilmState
    data class Success(val films: List<Film>) : HomeFilmState
}
