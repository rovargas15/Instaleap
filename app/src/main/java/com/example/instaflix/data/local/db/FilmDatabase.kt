package com.example.instaflix.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.instaflix.data.local.dto.FilmDto

@Database(entities = [FilmDto::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun movieDao(): FilmDao
}
