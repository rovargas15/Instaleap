package com.example.instaflix.data.dataSource

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.mapper.mapToFilm
import com.example.instaflix.data.mapper.mapToFilmEntity
import com.example.instaflix.data.mapper.mapToFilms
import com.example.instaflix.data.remote.model.FilmResponse

class LocalFilmDataSource(private val filmDao: FilmDao) {

    fun getAllFilms(category: String) = filmDao.getAllFilms(category).mapToFilms()

    fun insertFilms(films: List<FilmResponse>, category: String) =
        filmDao.insertFilms(films.mapToFilmEntity(category))

    fun getFilmByID(id: Long) = filmDao.getFilmById(id).mapToFilm()
}
