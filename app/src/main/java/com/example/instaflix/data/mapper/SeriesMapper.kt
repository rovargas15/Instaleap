package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.SeriesEntity
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Series

fun BaseResponse<SeriesResponse>.mapToBaseResult() = BaseResult(
    dates = this.dates?.mapToDateRange(),
    page = this.page,
    results = this.results.map { it.mapToSeries() },
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun SeriesResponse.mapToSeries() = Series(
    id = id,
    posterPath = posterPath,
    overview = overview,
    name = name,
    backdropPath = backdropPath,
    firstAirDate = firstAirDate,
    originalLanguage = originalLanguage,
    originalName = originalName,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun SeriesResponse.mapToSeriesEntity(category: String) = SeriesEntity(
    id = id,
    posterPath = posterPath,
    overview = overview,
    name = name,
    backdropPath = backdropPath ?: "",
    firstAirDate = firstAirDate,
    originalLanguage = originalLanguage,
    originalName = originalName,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    category = category,
)

fun SeriesEntity.mapToSeries() = Series(
    id = id,
    posterPath = posterPath,
    overview = overview,
    name = name,
    backdropPath = backdropPath,
    firstAirDate = firstAirDate,
    originalLanguage = originalLanguage,
    originalName = originalName,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
)
