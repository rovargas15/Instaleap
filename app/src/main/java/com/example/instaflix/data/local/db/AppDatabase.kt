package com.example.instaflix.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.instaflix.data.local.dto.FilmDto
import com.example.instaflix.data.local.dto.SeriesDto

@Database(entities = [FilmDto::class, SeriesDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FilmDao(): FilmDao
    abstract fun SeriesDao(): SeriesDao
}
