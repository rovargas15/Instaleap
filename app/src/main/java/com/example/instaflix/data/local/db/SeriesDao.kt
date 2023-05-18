package com.example.instaflix.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.instaflix.data.local.dto.SeriesEntity

@Dao
interface SeriesDao {

    @Query("SELECT * from SeriesEntity Where category == :category")
    fun getAllSeries(category: String): List<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: List<SeriesEntity>)

    @Query("SELECT * from SeriesEntity where  id == :seriesId")
    fun getSeriesById(seriesId: Long): SeriesEntity
}
