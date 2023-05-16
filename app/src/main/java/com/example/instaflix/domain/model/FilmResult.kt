package com.example.instaflix.domain.model

data class FilmResult(
    val dates: DateRange? = null,
    val page: Int,
    val results: List<Film>,
    val totalPages: Int,
    val totalResults: Int,
)
