package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmLocalRepository {

    fun getFilms(category: String): Flow<Result<List<Film>>>

    fun getFilmById(filmId: Long): Flow<Result<Film>>

    fun insertFilms(films: List<Film>, category: String)
}
