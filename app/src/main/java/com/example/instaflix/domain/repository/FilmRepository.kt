package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun getFilms(category: String): Flow<Result<List<Film>>>

    suspend fun updateFilm(category: String): Result<Unit>

    fun getFilmById(id: Long): Result<Film>
}
