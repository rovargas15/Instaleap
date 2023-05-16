package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.LocalFilmRepository

class GetFilmsByIdUC(
    private val localRepository: LocalFilmRepository,
) {

    fun invoke(id: Long) = localRepository.getFilmById(id)
}
