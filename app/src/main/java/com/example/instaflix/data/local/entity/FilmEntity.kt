package com.example.instaflix.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.instaflix.domain.model.Film

@Entity
data class FilmEntity(
    @PrimaryKey val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String,
) {

    fun mapperToFilm() = Film(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )

    companion object {
        fun mapperToFilmEntity(film: Film, category: String): FilmEntity = film.let {
            return FilmEntity(
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                category = category,
            )
        }
    }
}
