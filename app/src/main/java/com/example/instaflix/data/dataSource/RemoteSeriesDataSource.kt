package com.example.instaflix.data.dataSource

import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.remote.model.BaseResponse
import com.example.instaflix.data.remote.model.SeriesResponse
import com.example.instaflix.data.repository.BaseRepository

class RemoteSeriesDataSource(
    private val api: SeriesApi,
) : BaseRepository() {

    suspend fun getSeries(category: String): BaseResponse<SeriesResponse> = api.getSeries(category)
}
