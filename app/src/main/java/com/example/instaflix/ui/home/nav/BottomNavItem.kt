package com.example.instaflix.ui.home.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.instaflix.ui.utils.Category.PLAYING_NOW
import com.example.instaflix.ui.utils.Category.POPULAR
import com.example.instaflix.ui.utils.Category.UPCOMING
import com.example.instaflix.ui.utils.Route

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Home : BottomNavItem(POPULAR, Icons.Rounded.Home, Route.POPULAR)
    object Upcoming : BottomNavItem(UPCOMING, Icons.Rounded.List, Route.UPCOMING)
    object NowPlaying : BottomNavItem(PLAYING_NOW, Icons.Rounded.PlayArrow, Route.PLAYING_NOW)
}
