package com.example.instaflix.data.repository

import com.example.instaflix.data.mapper.mapToEntity
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.remote.model.FilmResultResponse
import com.example.instaflix.domain.model.FilmResult
import com.example.instaflix.domain.repository.LocalFilmRepository
import com.example.instaflix.domain.repository.RemoteFilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteFilmRepositoryImpl(
    private val api: FilmApi,
    private val localRepository: LocalFilmRepository,
) : BaseRepository(), RemoteFilmRepository {

    override fun getFilms(category: String): Flow<Result<FilmResult>> = flow {
        val response = launchSafe {
            val response = api.getFilms(category)
            saveInLocal(category, response)
            response.mapToEntity()
        }

        emit(Result.success(response))
    }.catch { error ->
        emit(Result.failure(error))
    }

    private fun saveInLocal(category: String, filmResultResponse: FilmResultResponse) {
        localRepository.insertFilms(filmResultResponse, category)
    }
}
