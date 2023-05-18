package com.example.instaflix.di

import com.example.instaflix.data.dataSource.LocalFilmDataSource
import com.example.instaflix.data.dataSource.RemoteFilmDataSource
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.repository.FilmRepositoryImpl
import com.example.instaflix.domain.repository.FilmRepository
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object FilmModule {

    @Provides
    @ViewModelScoped
    fun getFilmsByCategoryUCProvider(
        filmRepository: FilmRepository,
    ): GetFilmsByCategoryUC = GetFilmsByCategoryUC(
        filmRepository = filmRepository,
    )

    @Provides
    @ViewModelScoped
    fun getFilmsByIdUCProvider(
        filmRepository: FilmRepository,
    ): GetFilmsByIdUC = GetFilmsByIdUC(
        filmRepository = filmRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmRepositoryImplProvider(
        localFilmDataSource: LocalFilmDataSource,
        remoteFilmDataSource: RemoteFilmDataSource,
    ): FilmRepository = FilmRepositoryImpl(
        localFilmDataSource = localFilmDataSource,
        remoteFilmDataSource = remoteFilmDataSource,
    )

    @Provides
    @ViewModelScoped
    fun localFilmDataSourceProvider(
        filmDao: FilmDao,
    ): LocalFilmDataSource = LocalFilmDataSource(
        filmDao = filmDao,
    )

    @Provides
    @ViewModelScoped
    fun remoteFilmDataSourceProvider(
        api: FilmApi,
    ): RemoteFilmDataSource = RemoteFilmDataSource(
        api = api,
    )

    @Provides
    @ViewModelScoped
    fun filmApiProvider(retrofit: Retrofit): FilmApi = retrofit.create(FilmApi::class.java)
}
