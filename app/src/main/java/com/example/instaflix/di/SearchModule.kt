package com.example.instaflix.di

import com.example.instaflix.domain.repository.LocalFilmRepository
import com.example.instaflix.domain.usecase.GetFilmByQueryUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    @ViewModelScoped
    fun getFilmByQueryUCProvider(
        localRepository: LocalFilmRepository,
    ): GetFilmByQueryUC = GetFilmByQueryUC(localRepository = localRepository)
}
