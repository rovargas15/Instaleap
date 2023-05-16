package com.example.instaflix.di

import com.example.instaflix.domain.repository.LocalFilmRepository
import com.example.instaflix.domain.usecase.GetFilmsByIdUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailModule {

    @Provides
    @ViewModelScoped
    fun getFilmsByIdUCProvider(
        localRepository: LocalFilmRepository,
    ): GetFilmsByIdUC = GetFilmsByIdUC(localRepository = localRepository)
}
