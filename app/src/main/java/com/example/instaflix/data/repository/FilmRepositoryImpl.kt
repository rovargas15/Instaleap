package com.example.instaflix.data.repository

import com.example.instaflix.data.dataSource.LocalFilmDataSource
import com.example.instaflix.data.dataSource.RemoteFilmDataSource
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class FilmRepositoryImpl(
    private val localFilmDataSource: LocalFilmDataSource,
    private val remoteFilmDataSource: RemoteFilmDataSource,
) : BaseRepository(), FilmRepository {

    override fun getFilms(category: String): Flow<Result<List<Film>>> = launchSafe {
        localFilmDataSource.getAllFilms(category).transform {
            emit(Result.success(it))
        }
    }

    override suspend fun updateFilm(category: String) = launchResultSafe {
        val response = remoteFilmDataSource.getFilms(category)
        localFilmDataSource.insertFilms(response.results, category)
    }

    override fun getFilmById(id: Long) = launchResultSafe { localFilmDataSource.getFilmByID(id) }
}
