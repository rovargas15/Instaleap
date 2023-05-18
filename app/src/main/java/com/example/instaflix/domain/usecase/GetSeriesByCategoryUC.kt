package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository

class GetSeriesByCategoryUC(
    private val remoteRepository: SeriesRepository,
) {

    suspend fun invoke(category: String): Result<List<Series>> =
        remoteRepository.getSeries(category)
}
