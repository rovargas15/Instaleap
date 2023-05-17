package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class GetSeriesByIdUC(
    private val seriesRepository: SeriesRepository,
) {

    fun invoke(id: Long): Flow<Result<Series>> = seriesRepository.getSeriesById(id)
}
