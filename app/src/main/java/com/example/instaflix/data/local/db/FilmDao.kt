package com.example.instaflix.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.instaflix.data.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * from FilmEntity Where category == :category")
    fun getAll(category: String): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilms(films: List<FilmEntity>)

    @Query("SELECT * from FilmEntity where  id == :filmId")
    fun getFilmById(filmId: Long): Flow<FilmEntity>
}
