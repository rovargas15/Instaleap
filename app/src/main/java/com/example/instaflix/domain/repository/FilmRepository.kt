package com.example.instaflix.domain.repository

import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    suspend fun getFilms(category: String): Result<BaseResult<Film>>

    fun getLocalFilms(category: String): Flow<Result<List<Film>>>

    fun getFilmById(id: Long): Flow<Result<Film>>

    fun insertFilms(films: List<FilmResponse>, category: String)
}
