package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.repository.SeriesRepository

class GetSeriesByIdUC(
    private val seriesRepository: SeriesRepository,
) {

    fun invoke(id: Long) = seriesRepository.getSeriesById(id)
}
