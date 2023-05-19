package com.example.instaflix.data.repository

import com.example.instaflix.data.dataSource.LocalSeriesDataSource
import com.example.instaflix.data.dataSource.RemoteSeriesDataSource
import com.example.instaflix.domain.model.Series
import com.example.instaflix.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class SeriesRepositoryImpl(
    private val remoteSeriesDataSource: RemoteSeriesDataSource,
    private val localSeriesDataSource: LocalSeriesDataSource,
) : BaseRepository(), SeriesRepository {

    override fun getSeries(category: String): Flow<Result<List<Series>>> = launchSafe {
        localSeriesDataSource.getAllSeries(category).transform {
            emit(Result.success(it))
        }
    }

    override suspend fun updateSeries(category: String): Result<Unit> = launchResultSafe {
        val response = remoteSeriesDataSource.getSeries(category)
        localSeriesDataSource.insertSeries(response.results, category)
    }

    override fun getSeriesById(seriesId: Long) = launchResultSafe {
        localSeriesDataSource.getSeriesById(seriesId)
    }
}
