package com.example.instaflix.ui.home.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Slideshow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.instaflix.R
import com.example.instaflix.ui.utils.Route

sealed class BottomNavItem(@StringRes var title: Int, var icon: ImageVector, var route: String) {
    object Home : BottomNavItem(R.string.popular, Icons.Rounded.Star, Route.POPULAR)
    object Upcoming : BottomNavItem(R.string.upcoming, Icons.Rounded.Slideshow, Route.UPCOMING)
    object NowPlaying :
        BottomNavItem(R.string.now_playing, Icons.Rounded.PlayArrow, Route.PLAYING_NOW)
}
