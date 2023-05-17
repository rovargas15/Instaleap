package com.example.instaflix.data.remote.api

import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.FilmResponse
import com.example.instaflix.ui.utils.PATH.CATEGORY
import com.example.instaflix.ui.utils.Urls.FILMS
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmApi {

    @GET(FILMS)
    suspend fun getFilms(
        @Path(CATEGORY) category: String,
    ): BaseResponse<FilmResponse>
}
