package com.example.instaflix.data.repository

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.mapper.mapToDto
import com.example.instaflix.data.mapper.mapToEntity
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmRepositoryImpl(
    private val api: FilmApi,
    private val filmDao: FilmDao,
) : BaseRepository(), FilmRepository {

    override suspend fun getFilms(category: String): Result<BaseResult<Film>> = launchSafe {
        val response = api.getFilms(category)
        insertFilms(response, category)
        response.mapToEntity()
    }

    override fun getLocalFilms(category: String): Flow<Result<List<Film>>> =
        filmDao.getAll(category).map { result ->
            Result.success(result.mapToEntity())
        }

    override fun getFilmById(id: Long) = filmDao.getFilmById(id).map { result ->
        Result.success(result.mapToEntity())
    }

    override fun insertFilms(films: BaseResponse<FilmResponse>, category: String) =
        filmDao.insertFilms(
            films.mapToDto(category).results,
        )
}
