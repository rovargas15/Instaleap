package com.example.instaflix.data.repository

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.mapper.mapToDto
import com.example.instaflix.data.mapper.mapToEntity
import com.example.instaflix.data.remote.model.FilmResultResponse
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.LocalFilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalFilmRepositoryImpl(
    private val filmDao: FilmDao,
) : LocalFilmRepository {

    override fun getFilms(category: String) = filmDao.getAll(category).map { result ->
        Result.success(result.mapToEntity())
    }

    override fun getFilmById(filmId: Long) = filmDao.getFilmById(filmId).map { result ->
        Result.success(result.mapToEntity())
    }

    override fun getFilmByQuery(query: String): Flow<Result<List<Film>>> {
        TODO("Not yet implemented")
    }

    override fun insertFilms(films: FilmResultResponse, category: String) = filmDao.insertFilms(
        films.mapToDto(category).results,
    )
}
