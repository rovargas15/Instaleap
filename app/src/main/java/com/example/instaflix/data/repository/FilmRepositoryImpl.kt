package com.example.instaflix.data.repository

import com.example.instaflix.data.dataSource.LocalFilmDataSource
import com.example.instaflix.data.dataSource.RemoteFilmDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository

class FilmRepositoryImpl(
    private val localFilmDataSource: LocalFilmDataSource,
    private val remoteFilmDataSource: RemoteFilmDataSource,
) : BaseRepository(), FilmRepository {

    override suspend fun getFilms(category: String): Result<List<Film>> = launchSafe {
        val response = remoteFilmDataSource.getFilms(category)
        localFilmDataSource.insertFilms(response.results, category)
        response.mapToBaseResult().results
    }.recoverCatchingSafe {
        localFilmDataSource.getAllFilms(category)
    }

    override fun getLocalFilms(category: String): Result<List<Film>> = launchSafe {
        localFilmDataSource.getAllFilms(category)
    }

    override fun getFilmById(id: Long) = launchSafe { localFilmDataSource.getFilmByID(id) }
}
