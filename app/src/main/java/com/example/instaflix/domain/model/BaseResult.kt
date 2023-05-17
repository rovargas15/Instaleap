package com.example.instaflix.domain.model

data class BaseResult<R>(
    val dates: DateRange? = null,
    val page: Int,
    val results: List<R>,
    val totalPages: Int,
    val totalResults: Int,
)
