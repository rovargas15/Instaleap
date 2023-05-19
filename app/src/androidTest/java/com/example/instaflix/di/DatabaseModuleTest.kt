package com.example.instaflix.di

import com.example.instaflix.data.local.db.AppDatabase
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.db.SeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
object DatabaseModuleTest {

    private val appDatabase = mockk<AppDatabase>(relaxed = true)
    private val filmDao = mockk<FilmDao>(relaxed = true)
    private val seriesDao = mockk<SeriesDao>(relaxed = true)

    @Provides
    @Singleton
    fun provideAppDatabase() = appDatabase

    @Provides
    @Singleton
    fun filmDaoProvider() = filmDao

    @Provides
    @Singleton
    fun seriesDaoProvider() = seriesDao
}
