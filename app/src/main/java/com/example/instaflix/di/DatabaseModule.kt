package com.example.instaflix.di

import android.content.Context
import androidx.room.Room
import com.example.instaflix.data.local.db.FilmDao
import com.example.instaflix.data.local.db.FilmDatabase
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
    fun provideAppDatabase(@ApplicationContext context: Context): FilmDatabase {
        return Room.databaseBuilder(
            context,
            FilmDatabase::class.java,
            DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun userDaoProvider(appDatabase: FilmDatabase): FilmDao {
        return appDatabase.movieDao()
    }
}

private const val DB_NAME = "FilmDB"
