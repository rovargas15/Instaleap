package com.example.instaflix.data.remote.model

import com.squareup.moshi.Json

data class SeriesDetailResponse(
    val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "episode_run_time") val episodeRunTime: List<Int>,
    @Json(name = "first_air_date") val firstAirDate: String?,
    val genres: List<GenreResponse>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    @Json(name = "last_air_date") val lastAirDate: String?,
    val name: String,
    @Json(name = "next_episode_to_air") val nextEpisodeAir: String?,
    @Json(name = "number_of_episodes") val numberEpisodes: Int,
    @Json(name = "number_of_seasons") val numberSeasons: Int,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String,
    val seasons: List<SeasonResponse>,
    val status: String,
    val tagline: String,
    val type: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
)
