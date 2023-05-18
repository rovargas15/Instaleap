package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.repository.FilmRepository

class GetFilmsByIdUC(
    private val filmRepository: FilmRepository,
) {

    fun invoke(id: Long): Result<Film> = filmRepository.getFilmById(id)
}
