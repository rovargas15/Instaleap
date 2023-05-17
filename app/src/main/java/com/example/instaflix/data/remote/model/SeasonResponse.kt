package com.example.instaflix.data.remote.model

import com.squareup.moshi.Json

data class SeasonResponse(
    @Json(name = "air_date") val airDate: String,
    @Json(name = "episode_count") val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "season_number") val seasonNumber: Int,
)
