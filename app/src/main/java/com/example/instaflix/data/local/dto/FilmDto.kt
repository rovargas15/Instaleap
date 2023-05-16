package com.example.instaflix.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmDto(
    @PrimaryKey val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String,
)
