package com.example.instaflix.data.remote.api

import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.ui.utils.PATH.CATEGORY
import com.example.instaflix.ui.utils.Urls.SERIES
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesApi {
    @GET(SERIES)
    suspend fun getSeries(
        @Path(CATEGORY) category: String,
    ): BaseResponse<SeriesResponse>
}
