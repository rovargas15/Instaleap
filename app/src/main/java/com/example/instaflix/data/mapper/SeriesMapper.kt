package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.SeriesDto
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.Series

fun BaseResponse<SeriesResponse>.mapToEntity() = BaseResult(
    dates = this.dates?.mapToEntity(),
    page = this.page,
    results = this.results.map { it.mapToEntity() },
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun SeriesResponse.mapToEntity() = Series(
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

fun SeriesResponse.mapToDto(category: String) = SeriesDto(
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

fun SeriesDto.mapToEntity() = Series(
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
