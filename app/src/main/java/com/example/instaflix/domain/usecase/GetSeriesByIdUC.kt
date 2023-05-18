package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository

class GetSeriesByIdUC(
    private val seriesRepository: SeriesRepository,
) {

    fun invoke(id: Long): Result<Series> = seriesRepository.getSeriesById(id)
}
