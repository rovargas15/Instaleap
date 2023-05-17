package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.FilmRepository

class GetLocalFilmsByCategoryUC(
    private val filmRepository: FilmRepository,
) {

    fun invoke(category: String) = filmRepository.getLocalFilms(category)
}
