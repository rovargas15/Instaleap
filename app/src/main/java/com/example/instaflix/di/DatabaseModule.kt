package com.example.instaflix.di

import android.content.Context
import androidx.room.Room
import com.example.instaflix.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun filmDaoProvider(appDatabase: AppDatabase) = appDatabase.FilmDao()

    @Provides
    @Singleton
    fun seriesDaoProvider(appDatabase: AppDatabase) = appDatabase.SeriesDao()
}

private const val DB_NAME = "FilmDB"
