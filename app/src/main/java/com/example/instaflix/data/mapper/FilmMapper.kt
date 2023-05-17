package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.DateRangeResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.DateRange
import com.example.instaflix.domain.model.Film

fun BaseResponse<FilmResponse>.mapToBaseResult() = BaseResult(
    dates = this.dates?.mapToDateRange(),
    page = this.page,
    results = this.results.mapToFilms(),
    totalPages = this.totalPages,
    totalResults = this.totalResults,
)

fun DateRangeResponse.mapToDateRange() = DateRange(
    maximum = this.maximum,
    minimum = this.minimum,
)

fun List<FilmResponse>.mapToFilms() = this.map {
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

fun List<FilmResponse>.mapToFilmEntity(category: String) = this.map {
    FilmEntity(
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
