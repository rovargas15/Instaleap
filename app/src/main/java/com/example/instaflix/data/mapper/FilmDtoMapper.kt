package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.DateRangeDto
import com.example.instaflix.data.local.dto.FilmDto
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.DateRange
import com.example.instaflix.domain.model.Film

fun FilmDto.mapToEntity() = Film(
    adult = adult,
    backdropPath = backdropPath,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun List<FilmDto>.mapToEntity() = this.map {
    Film(
        adult = it.adult,
        backdropPath = it.backdropPath,
        id = it.id,
        originalLanguage = it.originalLanguage,
        originalTitle = it.originalTitle,
        overview = it.overview,
        popularity = it.popularity,
        posterPath = it.posterPath,
        releaseDate = it.releaseDate,
        title = it.title,
        video = it.video,
        voteAverage = it.voteAverage,
        voteCount = it.voteCount,
    )
}

fun BaseResult<FilmDto>.mapToEntity() = BaseResult<Film>(
    dates = this.dates,
    page = this.page,
    results = this.results.mapToEntity(),
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun DateRangeDto.mapToEntity() = DateRange(
    maximum = this.maximum,
    minimum = this.minimum,
)
