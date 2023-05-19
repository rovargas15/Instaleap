package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.FilmRepository

class UpdateFilmsDataUC(
    private val filmRepository: FilmRepository,
) {

    suspend fun invoke(category: String): Result<Unit> = filmRepository.updateFilm(category)
}
