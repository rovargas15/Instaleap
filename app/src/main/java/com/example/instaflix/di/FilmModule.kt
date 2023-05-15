package com.example.instaflix.di

import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.remote.api.FilmApi
import com.example.instaflix.data.repository.FilmLocalRepositoryImpl
import com.example.instaflix.data.repository.FilmRemoteRepositoryImpl
import com.example.instaflix.domain.repository.DomainExceptionRepository
import com.example.instaflix.domain.repository.FilmLocalRepository
import com.example.instaflix.domain.repository.FilmRemoteRepository
import com.example.instaflix.domain.usecase.GetFilmsUC
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
    fun getFilmsUCProvider(
        filmRemoteRepository: FilmRemoteRepository,
    ): GetFilmsUC = GetFilmsUC(
        remoteRepository = filmRemoteRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmRemoteRepositoryImplProvider(
        filmApi: FilmApi,
        domainExceptionRepository: DomainExceptionRepository,
        localRepository: FilmLocalRepository,
    ): FilmRemoteRepository = FilmRemoteRepositoryImpl(
        api = filmApi,
        exception = domainExceptionRepository,
        localRepository = localRepository,
    )

    @Provides
    @ViewModelScoped
    fun filmLocalRepositoryImplProvider(
        filmDao: FilmDao,
    ): FilmLocalRepository = FilmLocalRepositoryImpl(
        filmDao = filmDao,
    )

    @Provides
    @ViewModelScoped
    fun filmApiProvider(retrofit: Retrofit): FilmApi = retrofit.create(FilmApi::class.java)
}
