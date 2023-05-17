package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.domain.model.Film

fun FilmEntity.mapToBaseResult() = Film(
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

fun List<FilmEntity>.mapToFilms() = this.map {
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
