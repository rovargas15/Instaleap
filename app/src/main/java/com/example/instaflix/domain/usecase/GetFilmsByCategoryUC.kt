package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository

class GetFilmsByCategoryUC(
    private val filmRepository: FilmRepository,
) {

    suspend fun invoke(category: String): Result<BaseResult<Film>> = filmRepository.getFilms(category)
}
