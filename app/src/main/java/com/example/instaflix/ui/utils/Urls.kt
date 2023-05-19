package com.example.instaflix.ui.utils

import com.example.instaflix.BuildConfig
import com.example.instaflix.ui.utils.PATH.CATEGORY
import com.example.instaflix.ui.utils.PATH.FILM_ID

object Urls {
    const val BASE = "https://api.themoviedb.org/3/"
    const val FILMS = "movie/{$CATEGORY}?api_key=${BuildConfig.API_KEY}"
    const val SERIES = "tv/{$CATEGORY}?api_key=${BuildConfig.API_KEY}"
    const val FILM_DETAIL = "movie/$FILM_ID/?api_key=${BuildConfig.API_KEY}"
}

object UrlImage {
    const val w400 = "w400"
    const val w300 = "w300"
    const val w200 = "w300"
    const val BASE_IMAGE = "https://image.tmdb.org/t/p/"
}

object PATH {
    const val CATEGORY = "category"
    const val FILM_ID = "filmId"
}

object Category {
    const val UPCOMING = "upcoming"
    const val PLAYING_NOW = "now_playing"
    const val POPULAR = "popular"
    const val TOP_rated = "top_rated"
    const val ON_THE_AIR = "on_the_air"
}

object Graph {
    const val MAIN_GRAPH = "main_graph"
}

object Route {
    const val FILM = "Film/"
    const val SERIES = "Series/"
    const val FILM_DETAIL = "detail/"
}

object Parameter {
    const val FILM_ID = "filmId"
    const val SERIES_ID = "seriesId"
}
