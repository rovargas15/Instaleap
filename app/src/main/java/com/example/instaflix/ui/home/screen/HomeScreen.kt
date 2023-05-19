package com.example.instaflix.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.instaflix.R
import com.example.instaflix.ui.common.UiState
import com.example.instaflix.ui.home.nav.BottomNavItem
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.home.state.TopRatedSeriesUiState
import com.example.instaflix.ui.home.state.UpcomingUiState
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.ui.theme.LocalDimensions
import com.example.instaflix.ui.utils.Route
import com.example.instaflix.ui.utils.Tag
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun HomeScreen(
    navController: NavController,
    homeFilmViewModel: HomeFilmViewModel = hiltViewModel(),
    onSelectedItem: ((id: Long) -> Unit)? = null,
) {
    val backStackEntry: State<NavBackStackEntry?> = navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        backStackEntry.value?.destination?.route?.let { route ->
            homeFilmViewModel.onLoad(route)
        }
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomBar(navController, backStackEntry)
            }
        },
        content = { paddingValue ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValue),
            ) {
                val isMovie = Route.FILM == backStackEntry.value?.destination?.route
                if (isMovie) {
                    HomeContentMovie(
                        homeFilmViewModel = homeFilmViewModel,
                        onSelectedItem = onSelectedItem,
                    ) {
                        homeFilmViewModel.onLoad(Route.FILM)
                    }
                } else {
                    HomeContentSeries(
                        homeFilmViewModel = homeFilmViewModel,
                        onSelectedItem = onSelectedItem,
                    ) {
                        homeFilmViewModel.onLoad(Route.SERIES)
                    }
                }
            }
        },
    )
}

@Composable
private fun BottomBar(
    navController: NavController,
    backStackEntry: State<NavBackStackEntry?>,
) {
    val bottomNavItems = listOf(
        BottomNavItem.MOVIE,
        BottomNavItem.SERIES,
    )
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.icon} Icon",
                    )
                },
            )
        }
    }
}

@Composable
fun CreateCategoryItem(
    state: UiState,
    onSelectedItem: ((id: Long) -> Unit)? = null,
    onRetry: (() -> Unit),
) {
    val isError: Boolean = state.isError()
    when (state) {
        is UpcomingUiState -> {
            FilmCategoryItem(
                stringResource(id = R.string.title_upcoming),
                isLoading = state.isLoading,
                isError = isError,
                films = state.films,
                onSelectedItem = onSelectedItem,
                onRetry = onRetry,
            )
        }

        is PopularFilmsUiState -> {
            FilmCategoryItem(
                LocalContext.current.getString(R.string.title_popular),
                isLoading = state.isLoading,
                isError = isError,
                films = state.films,
                onSelectedItem = onSelectedItem,
                onRetry = onRetry,
            )
        }

        is PopularSeriesUiState -> {
            CategorySeriesItem(
                categoryTitle = LocalContext.current.getString(R.string.title_popular),
                series = state.seriesList,
                isLoading = state.isLoading,
                isError = isError,
                onSelectedItem = onSelectedItem,
                onRetry = onRetry,
            )
        }

        is TopRatedSeriesUiState -> {
            CategorySeriesItem(
                categoryTitle = LocalContext.current.getString(R.string.title_top_rated),
                series = state.seriesList,
                isLoading = state.isLoading,
                isError = isError,
                onSelectedItem = onSelectedItem,
                onRetry = onRetry,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    isPlaceholder: Boolean = false,
    onSelectedItem: (() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    OutlinedCard(
        onClick = { onSelectedItem?.invoke() },
        modifier = Modifier.testTag(Tag.LOADER).size(
            LocalDimensions.current.minSizeCard,
            LocalDimensions.current.heightCard,
        ).padding(
            horizontal = LocalDimensions.current.paddingMedium,
            vertical = LocalDimensions.current.paddingSmall,
        ).placeholder(
            visible = isPlaceholder,
            highlight = PlaceholderHighlight.shimmer(),
        ),
    ) {
        content?.invoke(this)
    }
}
