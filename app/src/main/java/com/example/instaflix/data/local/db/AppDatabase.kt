package com.example.instaflix.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.instaflix.data.local.dto.FilmEntity
import com.example.instaflix.data.local.dto.SeriesEntity

@Database(entities = [FilmEntity::class, SeriesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FilmDao(): FilmDao
    abstract fun SeriesDao(): SeriesDao
}
