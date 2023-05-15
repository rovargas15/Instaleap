package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRemoteRepository {

    fun getFilms(category: String): Flow<Result<List<Film>>>
}
