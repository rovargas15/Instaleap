package com.example.instaflix.data.repository

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.entity.FilmEntity
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmLocalRepository
import kotlinx.coroutines.flow.map

class FilmLocalRepositoryImpl(
    private val filmDao: FilmDao,
) : FilmLocalRepository {

    override fun getFilms(category: String) = filmDao.getAll(category).map { result ->
        Result.success(
            result.map {
                it.mapperToFilm()
            },
        )
    }

    override fun getFilmById(filmId: Long) = filmDao.getFilmById(filmId).map { result ->
        Result.success(result.mapperToFilm())
    }

    override fun insertFilms(films: List<Film>, category: String) = filmDao.insertFilms(
        films.map { film ->
            FilmEntity.mapperToFilmEntity(film, category)
        },
    )
}
