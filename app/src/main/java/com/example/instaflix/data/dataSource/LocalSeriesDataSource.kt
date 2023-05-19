package com.example.instaflix.data.dataSource

import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.mapper.mapToSeries
import com.example.instaflix.data.mapper.mapToSeriesEntity
import com.example.instaflix.data.remote.model.SeriesResponse
import kotlinx.coroutines.flow.map

class LocalSeriesDataSource(private val seriesDao: SeriesDao) {

    fun getAllSeries(category: String) = seriesDao.getAllSeries(category).map { entityList ->
        entityList.map { it.mapToSeries() }
    }

    fun insertSeries(series: List<SeriesResponse>, category: String) = seriesDao.insertSeries(
        series.map {
            it.mapToSeriesEntity(category)
        },
    )

    fun getSeriesById(id: Long) = seriesDao.getSeriesById(id).mapToSeries()
}
