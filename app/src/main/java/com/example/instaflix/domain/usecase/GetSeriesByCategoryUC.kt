package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository

class GetSeriesByCategoryUC(
    private val remoteRepository: SeriesRepository,
) {

    suspend fun invoke(category: String): Result<BaseResult<Series>> =
        remoteRepository.getSeries(category)
}
