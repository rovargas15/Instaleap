package com.example.instaflix.data.local.dto

data class FilmResultDto(
    val dates: DateRangeDto? = null,
    val page: Int,
    val results: List<FilmDto>,
    val totalPages: Int,
    val totalResults: Int,
)
