package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.domain.model.Film

fun FilmEntity.mapToFilm() = Film(
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
    it.mapToFilm()
}
