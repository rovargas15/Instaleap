package com.example.instaflix.data.dataSource

import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.mapper.mapToSeries
import com.example.instaflix.data.mapper.mapToSeriesEntity
import com.example.instaflix.data.remote.model.SeriesResponse

class LocalSeriesDataSource(private val seriesDao: SeriesDao) {

    fun getAllSeries(category: String) = seriesDao.getAllSeries(category).map {
        it.mapToSeries()
    }

    fun insertSeries(films: List<SeriesResponse>, category: String) = seriesDao.insertSeries(
        films.map {
            it.mapToSeriesEntity(category)
        },
    )

    fun getSeriesById(id: Long) = seriesDao.getSeriesById(id).mapToSeries()
}
