package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.FilmRepository

class GetFilmsByIdUC(
    private val filmRepository: FilmRepository,
) {

    fun invoke(id: Long) = filmRepository.getFilmById(id)
}
