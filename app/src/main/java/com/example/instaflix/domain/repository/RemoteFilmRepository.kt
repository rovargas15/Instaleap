package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.FilmResult

interface RemoteFilmRepository {

    suspend fun getFilms(category: String): Result<FilmResult>
}
