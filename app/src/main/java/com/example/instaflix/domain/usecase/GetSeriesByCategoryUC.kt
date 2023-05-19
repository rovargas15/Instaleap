package com.example.instaflix.domain.usecase

import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow

class GetSeriesByCategoryUC(
    private val remoteRepository: SeriesRepository,
) {

    fun invoke(category: String): Flow<Result<List<Series>>> = remoteRepository.getSeries(category)
}
