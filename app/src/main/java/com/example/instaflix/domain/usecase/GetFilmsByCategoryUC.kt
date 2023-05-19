package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmsByCategoryUC(
    private val filmRepository: FilmRepository,
) {

    fun invoke(category: String): Flow<Result<List<Film>>> = filmRepository.getFilms(category)
}
