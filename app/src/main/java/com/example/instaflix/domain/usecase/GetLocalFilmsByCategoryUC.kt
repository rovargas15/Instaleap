package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.LocalFilmRepository

class GetLocalFilmsByCategoryUC(
    private val localFilmRepository: LocalFilmRepository,
) {

    fun invoke(category: String) = localFilmRepository.getFilms(category)
}
