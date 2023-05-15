package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.FilmRemoteRepository

class GetFilmsUC(
    private val remoteRepository: FilmRemoteRepository,
) {

    fun invoke(category: String) = remoteRepository.getFilms(category)
}
