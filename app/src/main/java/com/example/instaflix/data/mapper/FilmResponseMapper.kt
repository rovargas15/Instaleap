package com.example.instaflix.data.mapper

import com.example.instaflix.data.local.dto.DateRangeDto
import com.example.instaflix.data.local.dto.FilmDto
import com.example.instaflix.data.local.dto.FilmResultDto
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.DateRangeResponse
import com.example.instaflix.data.remote.model.FilmDetailResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.data.remote.model.GenreResponse
import com.example.instaflix.data.remote.model.SeasonResponse
import com.example.instaflix.data.remote.model.SeriesDetailResponse
import com.example.instaflix.domain.model.BaseResult
import com.example.instaflix.domain.model.DateRange
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.FilmDetail
import com.example.instaflix.domain.model.Genre
import com.example.instaflix.domain.model.Season
import com.example.instaflix.domain.model.SeriesDetail

fun BaseResponse<FilmResponse>.mapToEntity() = BaseResult(
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

fun BaseResponse<FilmResponse>.mapToDto(category: String) = FilmResultDto(
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

fun FilmDetailResponse.mapToEntity() = FilmDetail(
    adult = adult,
    backdropPath = backdropPath,
    budget = budget,
    genres = genres.map { it.mapToEntity() },
    homepage = homepage,
    id = id,
    imdb_id = imdb_id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    poster_path = posterPath,
    release_date = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun GenreResponse.mapToEntity() = Genre(
    id,
    name,
)

fun SeasonResponse.mapToEntity() = Season(
    id = id,
    airDate = airDate,
    episodeCount = episodeCount,
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
)

fun SeriesDetailResponse.mapToEntity() = SeriesDetail(
    adult = adult,
    backdropPath = backdropPath,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    genres = genres.map { it.mapToEntity() },
    homepage = homepage,
    id = id,
    in_production = in_production,
    languages = languages,
    lastAirDate = lastAirDate,
    name = name,
    nextEpisodeAir = nextEpisodeAir,
    numberEpisodes = numberEpisodes,
    numberSeasons = numberSeasons,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    seasons = seasons.map { it.mapToEntity() },
    status = status,
    tagline = tagline,
    type = type,
    voteAverage = voteAverage,
    voteCount = voteCount,

)
