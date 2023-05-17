package com.example.instaflix.domain.repository

import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getSeries(category: String): Result<BaseResult<Series>>

    fun getSeriesById(seriesId: Long): Flow<Result<Series>>

    fun getLocalSeries(category: String): Flow<Result<List<Series>>>

    fun insertSeries(series: List<SeriesResponse>, category: String)
}
