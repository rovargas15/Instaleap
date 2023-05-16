package com.example.instaflix.domain.repository

import com.example.instaflix.data.remote.model.FilmResultResponse
import com.example.instaflix.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface LocalFilmRepository {

    fun getFilms(category: String): Flow<Result<List<Film>>>

    fun getFilmById(filmId: Long): Flow<Result<Film>>

    fun getFilmByQuery(query: String): Flow<Result<List<Film>>>

    fun insertFilms(films: FilmResultResponse, category: String)
}
