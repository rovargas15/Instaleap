package com.example.instaflix.di

import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.repository.SeriesRepositoryImpl
import com.example.instaflix.domain.repository.SeriesRepository
import com.example.instaflix.domain.usecase.GetLocalSeriesByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SeriesModule {

    @Provides
    @ViewModelScoped
    fun getSeriesByCategoryUCProvider(
        seriesRepository: SeriesRepository,
    ): GetSeriesByCategoryUC = GetSeriesByCategoryUC(
        remoteRepository = seriesRepository,
    )

    @Provides
    @ViewModelScoped
    fun getLocalSeriesByCategoryUCProvider(
        seriesRepository: SeriesRepository,
    ): GetLocalSeriesByCategoryUC = GetLocalSeriesByCategoryUC(
        remoteRepository = seriesRepository,
    )

    @Provides
    @ViewModelScoped
    fun getSeriesByIdUCProvider(
        seriesRepository: SeriesRepository,
    ): GetSeriesByIdUC = GetSeriesByIdUC(
        seriesRepository = seriesRepository,
    )

    @Provides
    @ViewModelScoped
    fun seriesRepositoryImplProvider(
        api: SeriesApi,
        seriesDao: SeriesDao,
    ): SeriesRepository = SeriesRepositoryImpl(
        api = api,
        seriesDao = seriesDao,
    )

    @Provides
    @ViewModelScoped
    fun seriesApiProvider(retrofit: Retrofit): SeriesApi = retrofit.create(SeriesApi::class.java)
}
