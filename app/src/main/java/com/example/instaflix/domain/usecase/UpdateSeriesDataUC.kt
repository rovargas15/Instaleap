package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.SeriesRepository

class UpdateSeriesDataUC(
    private val seriesRepository: SeriesRepository,
) {

    suspend fun invoke(category: String): Result<Unit> = seriesRepository.updateSeries(category)
}
