package com.example.instaflix.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResultResponse(
    val dates: DateRangeResponse? = null,
    val page: Int,
    val results: List<FilmResponse>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int,
)
