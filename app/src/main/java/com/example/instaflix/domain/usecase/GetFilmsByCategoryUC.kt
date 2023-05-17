package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.FilmRepository

class GetFilmsByCategoryUC(
    private val remoteRepository: FilmRepository,
) {

    suspend fun invoke(category: String) = remoteRepository.getFilms(category)
}
