package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Film

interface FilmRepository {

    suspend fun getFilms(category: String): Result<List<Film>>

    fun getLocalFilms(category: String): Result<List<Film>>

    fun getFilmById(id: Long): Result<Film>
}
