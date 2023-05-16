package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.RemoteFilmRepository

class GetFilmsByCategoryUC(
    private val remoteRepository: RemoteFilmRepository,
) {

    suspend fun invoke(category: String) = remoteRepository.getFilms(category)
}
