package com.example.instaflix.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.instaflix.R
import com.example.instaflix.ui.common.SimpleSnackbar
import com.example.instaflix.ui.common.UiState
import com.example.instaflix.ui.home.nav.BottomNavItem
import com.example.instaflix.ui.home.state.FilmsNowPlayingUiState
import com.example.instaflix.ui.home.state.OnTheAirSeriesUiState
import com.example.instaflix.ui.home.state.PopularFilmsUiState
import com.example.instaflix.ui.home.state.PopularSeriesUiState
import com.example.instaflix.ui.home.state.TopRatedSeriesUiState
import com.example.instaflix.ui.home.state.UpcomingUiState
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.ui.theme.LocalDimensions
import com.example.instaflix.ui.utils.Route
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

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { AppTopBar() },
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SimpleSnackbar(snackbarHostState)
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                BottomBar(navController, backStackEntry)
            }
        },
        content = { paddingValue ->
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .padding(paddingValue),
            ) {
                val isMovie = Route.MOVIE == backStackEntry.value?.destination?.route
                if (isMovie) {
                    HomeContentMovie(
                        homeFilmViewModel = homeFilmViewModel,
                        snackbarHostState = snackbarHostState,
                        onSelectedItem = onSelectedItem,
                    )
                } else {
                    HomeContentSeries(
                        homeFilmViewModel = homeFilmViewModel,
                        snackbarHostState = snackbarHostState,
                        onSelectedItem = onSelectedItem,
                    )
                }
            }
        },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppTopBar() {
    TopAppBar(
        title = {
            Text(
                LocalContext.current.getString(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
            )
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
    NavigationBar() {
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
fun CreateCategoryFilms(
    state: UiState,
    snackbarHostState: SnackbarHostState,
    onSelectedItem: ((id: Long) -> Unit)? = null,
) {
    val dismissLabel = LocalContext.current.getString(R.string.snackbar_button_dismiss)
    if (state.isError()) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                message = state.getErrorMessage(),
                actionLabel = dismissLabel,
                duration = SnackbarDuration.Short,
            )
        }
    }

    when (state) {
        is UpcomingUiState -> {
            CategoryFilms(
                LocalContext.current.getString(R.string.title_upcoming),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }

        is FilmsNowPlayingUiState -> {
            CategoryFilms(
                LocalContext.current.getString(R.string.title_films_now_playing),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }

        is PopularFilmsUiState -> {
            CategoryFilms(
                LocalContext.current.getString(R.string.title_popular),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }

        is PopularSeriesUiState -> {
            CategorySeries(
                LocalContext.current.getString(R.string.title_popular),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }

        is TopRatedSeriesUiState -> {
            CategorySeries(
                LocalContext.current.getString(R.string.title_top_rated),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }

        is OnTheAirSeriesUiState -> {
            CategorySeries(
                LocalContext.current.getString(R.string.title_on_the_air),
                state.films,
                state.isLoading,
                onSelectedItem,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmCard(
    isPlaceholder: Boolean = false,
    onSelectedItem: (() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    OutlinedCard(
        onClick = { onSelectedItem?.invoke() },
        modifier = Modifier.size(
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
