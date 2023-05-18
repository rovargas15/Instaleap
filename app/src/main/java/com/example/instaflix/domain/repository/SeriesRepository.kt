package com.example.instaflix.domain.repository

import com.example.instaflix.domain.model.Series

interface SeriesRepository {

    suspend fun getSeries(category: String): Result<List<Series>>

    fun getSeriesById(seriesId: Long): Result<Series>
}
