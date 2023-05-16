package com.example.instaflix.ui.search.state

import com.example.instaflix.domain.model.Film

sealed interface SearchFilmState {
    object Loader : SearchFilmState
    object Error : SearchFilmState
    data class Success(val film: List<Film>) : SearchFilmState
}
