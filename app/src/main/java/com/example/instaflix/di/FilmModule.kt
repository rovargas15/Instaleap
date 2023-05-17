package com.example.instaflix.di

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.repository.FilmRepositoryImpl
import com.example.instaflix.domain.repository.FilmRepository
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import com.example.instaflix.domain.usecase.GetLocalFilmsByCategoryUC
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
    fun getFilmsLocalByCategoryUCProvider(
        filmRepository: FilmRepository,
    ): GetLocalFilmsByCategoryUC = GetLocalFilmsByCategoryUC(
        filmRepository = filmRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmRepositoryImplProvider(
        filmApi: FilmApi,
        filmDao: FilmDao,
    ): FilmRepository = FilmRepositoryImpl(
        api = filmApi,
        filmDao = filmDao,
    )

    @Provides
    @ViewModelScoped
    fun filmApiProvider(retrofit: Retrofit): FilmApi = retrofit.create(FilmApi::class.java)
}
