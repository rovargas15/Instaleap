package com.example.instaflix.data.repository

import com.example.instaflix.data.dataSource.LocalSeriesDataSource
import com.example.instaflix.data.dataSource.RemoteSeriesDataSource
import com.example.instaflix.data.mapper.mapToBaseResult
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository

class SeriesRepositoryImpl(
    private val remoteSeriesDataSource: RemoteSeriesDataSource,
    private val localSeriesDataSource: LocalSeriesDataSource,
) : BaseRepository(), SeriesRepository {

    override suspend fun getSeries(category: String): Result<List<Series>> = launchSafe {
        val response = remoteSeriesDataSource.getSeries(category)
        localSeriesDataSource.insertSeries(response.results, category)
        response.mapToBaseResult().results
    }.recoverCatchingSafe {
        localSeriesDataSource.getAllSeries(category)
    }

    override fun getSeriesById(seriesId: Long) = launchSafe {
        localSeriesDataSource.getSeriesById(seriesId)
    }
}
