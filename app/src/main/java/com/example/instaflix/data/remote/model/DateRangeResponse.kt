package com.example.instaflix.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DateRangeResponse(
    val maximum: String,
    val minimum: String,
)
