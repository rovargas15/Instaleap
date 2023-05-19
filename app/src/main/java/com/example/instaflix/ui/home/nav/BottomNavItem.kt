package com.example.instaflix.ui.home.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Slideshow
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.instaflix.R
import com.example.instaflix.ui.utils.Route

sealed class BottomNavItem(@StringRes var title: Int, var icon: ImageVector, var route: String) {
    object MOVIE : BottomNavItem(R.string.movie, Icons.Rounded.Movie, Route.FILM)
    object SERIES : BottomNavItem(R.string.series, Icons.Rounded.Slideshow, Route.SERIES)
}
