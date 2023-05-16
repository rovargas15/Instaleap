package com.example.instaflix.data.repository

import com.example.instaflix.data.mapper.mapToEntity
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.domain.repository.LocalFilmRepository
import com.example.instaflix.domain.repository.RemoteFilmRepository

class RemoteFilmRepositoryImpl(
    private val api: FilmApi,
    private val localRepository: LocalFilmRepository,
) : BaseRepository(), RemoteFilmRepository {

    override suspend fun getFilms(category: String) = launchSafe {
        api.getFilms(category).let { filmResultResponse ->
            localRepository.insertFilms(filmResultResponse, category)
            filmResultResponse.mapToEntity()
        }
    }
}
