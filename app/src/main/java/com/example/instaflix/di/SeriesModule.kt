package com.example.instaflix.di

import com.example.instaflix.data.dataSource.LocalSeriesDataSource
import com.example.instaflix.data.dataSource.RemoteSeriesDataSource
import com.example.instaflix.data.local.db.SeriesDao
import com.example.instaflix.data.remote.api.SeriesApi
import com.example.instaflix.data.repository.SeriesRepositoryImpl
import com.example.instaflix.domain.repository.SeriesRepository
import com.example.instaflix.domain.usecase.GetSeriesByCategoryUC
import com.example.instaflix.domain.usecase.GetSeriesByIdUC
import com.example.instaflix.domain.usecase.UpdateSeriesDataUC
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
    fun updateSeriesDataUCProvider(
        seriesRepository: SeriesRepository,
    ): UpdateSeriesDataUC = UpdateSeriesDataUC(
        seriesRepository = seriesRepository,
    )

    @Provides
    @ViewModelScoped
    fun getSeriesByCategoryUCProvider(
        seriesRepository: SeriesRepository,
    ): GetSeriesByCategoryUC = GetSeriesByCategoryUC(
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
        localSeriesDataSource: LocalSeriesDataSource,
        remoteSeriesDataSource: RemoteSeriesDataSource,
    ): SeriesRepository = SeriesRepositoryImpl(
        localSeriesDataSource = localSeriesDataSource,
        remoteSeriesDataSource = remoteSeriesDataSource,
    )

    @Provides
    @ViewModelScoped
    fun localSeriesDataSourceProvider(
        dao: SeriesDao,
    ): LocalSeriesDataSource = LocalSeriesDataSource(
        seriesDao = dao,
    )

    @Provides
    @ViewModelScoped
    fun remoteSeriesDataSourceProvider(
        api: SeriesApi,
    ): RemoteSeriesDataSource = RemoteSeriesDataSource(
        api = api,
    )

    @Provides
    @ViewModelScoped
    fun seriesApiProvider(retrofit: Retrofit): SeriesApi = retrofit.create(SeriesApi::class.java)
}
