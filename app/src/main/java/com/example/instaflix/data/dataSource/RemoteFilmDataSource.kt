package com.example.instaflix.data.dataSource

import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.repository.BaseRepository

class RemoteFilmDataSource(
    private val api: FilmApi,
) : BaseRepository() {

    suspend fun getFilms(category: String) = api.getFilms(category)
}
