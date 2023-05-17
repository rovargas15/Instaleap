package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmsByIdUC(
    private val filmRepository: FilmRepository,
) {

    fun invoke(id: Long): Flow<Result<Film>> = filmRepository.getFilmById(id)
}
