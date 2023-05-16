package com.example.instaflix.ui.utils

import com.example.instaflix.BuildConfig
import com.example.instaflix.ui.utils.PATH.CATEGORY
import com.example.instaflix.ui.utils.PATH.FILM_ID

object Urls {
    const val BASE = "https://api.themoviedb.org/3/"
    const val FILMS = "movie/{$CATEGORY}?api_key=${BuildConfig.API_KEY}"
    const val FILM_DETAIL = "movie/{$FILM_ID}?api_key=${BuildConfig.API_KEY}"
}

object UrlImage {
    const val W300 = "w300"
    const val BASE_IMAGE = "https://image.tmdb.org/t/p/$W300/"
}

object PATH {
    const val CATEGORY = "category"
    const val FILM_ID = "filmId"
}

object Category {
    const val UPCOMING = "upcoming"
    const val PLAYING_NOW = "now_playing"
    const val POPULAR = "popular"
}

object Graph {
    const val MAIN_GRAPH = "main_graph"
}

object Route {
    const val POPULAR = "popular"
    const val UPCOMING = "upcoming"
    const val PLAYING_NOW = "now_playing"
    const val SEARCH = "search"
    const val FILM_DETAIL = "detail/"
}

object Parameter {
    const val FILM_ID = "filmId"
}
