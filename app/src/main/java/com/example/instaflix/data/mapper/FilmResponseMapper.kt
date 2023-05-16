package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.DateRangeDto
import com.example.instaflix.data.local.dto.FilmDto
import com.example.instaflix.data.local.dto.FilmResultDto
import com.example.instaflix.data.remote.model.DateRangeResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.data.remote.model.FilmResultResponse
import com.example.instaflix.domain.model.DateRange
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.FilmResult

fun FilmResultResponse.mapToEntity() = FilmResult(
    dates = this.dates?.mapToEntity(),
    page = this.page,
    results = this.results.mapToEntity(),
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun DateRangeResponse.mapToEntity() = DateRange(
    maximum = this.maximum,
    minimum = this.minimum,
)

fun List<FilmResponse>.mapToEntity() = this.map {
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

fun FilmResultResponse.mapToDto(category: String) = FilmResultDto(
    dates = this.dates?.mapToDto(),
    page = this.page,
    results = this.results.mapToDto(category),
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun DateRangeResponse.mapToDto() = DateRangeDto(
    maximum = this.maximum,
    minimum = this.minimum,
)

fun List<FilmResponse>.mapToDto(category: String) = this.map {
    FilmDto(
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
        category = category,
    )
}
