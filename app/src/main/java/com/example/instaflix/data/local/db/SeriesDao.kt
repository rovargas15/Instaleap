package com.example.instaflix.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.instaflix.data.local.dto.SeriesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {

    @Query("SELECT * from SeriesDto Where category == :category")
    fun getAllSeries(category: String): Flow<List<SeriesDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: List<SeriesDto>)

    @Query("SELECT * from SeriesDto where  id == :seriesId")
    fun getSeriesById(seriesId: Long): Flow<SeriesDto>
}
