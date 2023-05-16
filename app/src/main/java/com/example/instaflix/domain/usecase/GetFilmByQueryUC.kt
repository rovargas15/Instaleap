package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.LocalFilmRepository

class GetFilmByQueryUC(
    private val localRepository: LocalFilmRepository,
) {

    fun invoke(query: String) = localRepository.getFilmByQuery(query)
}
