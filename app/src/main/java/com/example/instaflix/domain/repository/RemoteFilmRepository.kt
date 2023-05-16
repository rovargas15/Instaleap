package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.FilmResult
import kotlinx.coroutines.flow.Flow

interface RemoteFilmRepository {

    fun getFilms(category: String): Flow<Result<FilmResult>>
}
