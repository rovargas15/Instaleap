package com.example.instaflix.data.repository

import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.domain.exception.InternalErrorException
import com.example.instaflix.domain.repository.DomainExceptionRepository
import com.example.instaflix.domain.repository.FilmLocalRepository
import com.example.instaflix.domain.repository.FilmRemoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FilmRemoteRepositoryImpl(
    private val api: FilmApi,
    private val exception: DomainExceptionRepository,
    private val localRepository: FilmLocalRepository,
) : FilmRemoteRepository {

    override fun getFilms(category: String) = flow {
        val response = api.getFilms(category)
        val films = response.results
        localRepository.insertFilms(
            films.map {
                it.mapperToFilm()
            },
            category,
        )
        emit(
            Result.success(
                films.map {
                    it.mapperToFilm()
                },
            ),
        )
    }.catch { error ->
        if (error is InternalErrorException) {
            localRepository.getFilms(category)
        } else {
            emit(Result.failure(exception.manageError(error)))
        }
    }
}
