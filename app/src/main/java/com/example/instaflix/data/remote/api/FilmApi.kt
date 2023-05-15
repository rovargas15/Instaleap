package com.example.instaflix.data.remote.api

import com.example.instaflix.data.remote.model.ResultResponse
import com.example.instaflix.ui.utils.PATH.CATEGORY
import com.example.instaflix.ui.utils.PATH.FILM_ID
import com.example.instaflix.ui.utils.Urls.FILMS
import com.example.instaflix.ui.utils.Urls.FILM_DETAIL
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmApi {

    @GET(FILMS)
    suspend fun getFilms(@Path(CATEGORY) category: String): ResultResponse

    @GET(FILM_DETAIL)
    suspend fun getFilmDetail(@Path(FILM_ID) filmId: String): ResultResponse
}
