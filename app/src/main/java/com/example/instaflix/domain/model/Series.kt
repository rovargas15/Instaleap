package com.example.instaflix.domain.model

data class Series(
    val id: Long,
    val backdropPath: String?,
    val firstAirDate: String,
    val name: String,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
)
