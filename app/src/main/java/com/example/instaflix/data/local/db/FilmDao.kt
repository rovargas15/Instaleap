package com.example.instaflix.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.instaflix.data.local.dto.FilmDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * from FilmDto Where category == :category")
    fun getAll(category: String): Flow<List<FilmDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilms(films: List<FilmDto>)

    @Query("SELECT * from FilmDto where  id == :filmId")
    fun getFilmById(filmId: Long): Flow<FilmDto>
}
