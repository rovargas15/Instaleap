package com.example.instaflix.di

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.repository.LocalFilmRepositoryImpl
import com.example.instaflix.data.repository.RemoteFilmRepositoryImpl
import com.example.instaflix.domain.repository.LocalFilmRepository
import com.example.instaflix.domain.repository.RemoteFilmRepository
import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
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
        remoteFilmRepository: RemoteFilmRepository,
    ): GetFilmsByCategoryUC = GetFilmsByCategoryUC(
        remoteRepository = remoteFilmRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmRemoteRepositoryImplProvider(
        filmApi: FilmApi,
        localRepository: LocalFilmRepository,
    ): RemoteFilmRepository = RemoteFilmRepositoryImpl(
        api = filmApi,
        localRepository = localRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmLocalRepositoryImplProvider(
        filmDao: FilmDao,
    ): LocalFilmRepository = LocalFilmRepositoryImpl(
        filmDao = filmDao,
    )

    @Provides
    @ViewModelScoped
    fun filmApiProvider(retrofit: Retrofit): FilmApi = retrofit.create(FilmApi::class.java)
}
