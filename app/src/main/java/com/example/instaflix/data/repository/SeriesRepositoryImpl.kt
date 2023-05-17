package com.example.instaflix.data.repository

import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.data.mapper.mapToSeries
import com.example.instaflix.data.mapper.mapToSeriesEntity
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.map

class SeriesRepositoryImpl(
    private val api: SeriesApi,
    private val seriesDao: SeriesDao,
) : BaseRepository(), SeriesRepository {

    override suspend fun getSeries(category: String): Result<BaseResult<Series>> = launchSafe {
        val response = api.getSeries(category)
        insertSeries(response.results, category)
        response.mapToBaseResult()
    }

    override fun getLocalSeries(category: String) = seriesDao.getAllSeries(category).map { result ->
        Result.success(result.map { it.mapToSeries() })
    }

    override fun getSeriesById(seriesId: Long) = seriesDao.getSeriesById(seriesId).map { result ->
        Result.success(result.mapToSeries())
    }

    override fun insertSeries(series: List<SeriesResponse>, category: String) {
        seriesDao.insertSeries(
            series.map {
                it.mapToSeriesEntity(category)
            },
        )
    }
}
