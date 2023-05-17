package com.example.instaflix.data.repository

import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.mapper.mapToDto
import com.example.instaflix.data.mapper.mapToEntity
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.remote.model.BaseResponse
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
        insertSeries(response, category)
        response.mapToEntity()
    }

    override fun getLocalSeries(category: String) = seriesDao.getAllSeries(category).map { result ->
        Result.success(result.map { it.mapToEntity() })
    }

    override fun getSeriesById(seriesId: Long) = seriesDao.getSeriesById(seriesId).map { result ->
        Result.success(result.mapToEntity())
    }

    override fun insertSeries(series: BaseResponse<SeriesResponse>, category: String) {
        seriesDao.insertSeries(
            series.results.map {
                it.mapToDto(category)
            },
        )
    }
}
