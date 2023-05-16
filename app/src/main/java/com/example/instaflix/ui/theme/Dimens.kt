package com.example.instaflix.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingXLarge: Dp = 16.dp,
    val borderSmall: Dp = 1.dp,
    val imageXSmall: Dp = 150.dp,
    val imageSmall: Dp = 230.dp,
    val heightCard: Dp = 270.dp,
    val minSizeCard: Dp = 150.dp,
)

internal val LocalDimensions = staticCompositionLocalOf { Dimens() }
