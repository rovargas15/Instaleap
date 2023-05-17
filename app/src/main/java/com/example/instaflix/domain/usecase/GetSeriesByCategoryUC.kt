package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.SeriesRepository

class GetSeriesByCategoryUC(
    private val remoteRepository: SeriesRepository,
) {

    suspend fun invoke(category: String) = remoteRepository.getSeries(category)
}
