package com.example.instaflix.ui.home.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Slideshow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.instaflix.R
import com.example.instaflix.ui.utils.Route

sealed class BottomNavItem(@StringRes var title: Int, var icon: ImageVector, var route: String) {
    object MOVIE : BottomNavItem(R.string.movie, Icons.Rounded.Star, Route.FILM)
    object SERIES : BottomNavItem(R.string.series, Icons.Rounded.Slideshow, Route.SERIES)
}
