package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun getSeries(category: String): Flow<Result<List<Series>>>

    suspend fun updateSeries(category: String): Result<Unit>

    fun getSeriesById(seriesId: Long): Result<Series>
}
