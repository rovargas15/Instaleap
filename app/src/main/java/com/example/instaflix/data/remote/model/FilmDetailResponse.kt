package com.example.instaflix.data.remote.model

import com.squareup.moshi.Json

data class FilmDetailResponse(
    val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String,
    val budget: Int,
    val genres: List<GenreResponse>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
)
