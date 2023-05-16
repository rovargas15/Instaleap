package com.example.instaflix.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest.Builder
import com.example.instaflix.R
import com.example.instaflix.domain.model.Film
import com.example.instaflix.ui.common.ContentError
import com.example.instaflix.ui.home.nav.BottomNavItem
import com.example.instaflix.ui.home.state.HomeFilmState
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.ui.theme.LocalDimensions
import com.example.instaflix.ui.utils.UrlImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun HomeScreen(
    category: String,
    navController: NavController,
    viewModel: HomeFilmViewModel = hiltViewModel(),
    onSelectedFilm: ((film: Film) -> Unit)? = null,
) {
    val state = viewModel.viewState.observeAsState()
    if (state.value == null) {
        viewModel.getFilmByCategory(category)
    }
    ContentHome(navController, state, onSelectedFilm) {
        viewModel.getFilmByCategory(category)
    }
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
) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Upcoming,
        BottomNavItem.NowPlaying,
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar() {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = item.title,
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
private fun ContentHome(
    navController: NavController,
    state: State<HomeFilmState?>,
    onSelectedFilm: ((film: Film) -> Unit)?,
    onRetry: (() -> Unit),
) {
    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                BottomBar(navController)
            }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (val value = state.value) {
                    is HomeFilmState.Loader -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = LocalDimensions.current.minSizeCard),
                            horizontalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
                            verticalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
                            modifier = Modifier.padding(
                                all = LocalDimensions.current.paddingMedium,
                            ).testTag("listFilm"),
                        ) {
                            items(10) {
                                FilmCard(isPlaceholder = true)
                            }
                        }
                    }

                    is HomeFilmState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = LocalDimensions.current.minSizeCard),
                            horizontalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
                            verticalArrangement = Arrangement.spacedBy(space = LocalDimensions.current.paddingMedium),
                            modifier = Modifier.padding(
                                all = LocalDimensions.current.paddingMedium,
                            ).testTag("listFilm"),
                        ) {
                            items(value.films.size) { index ->
                                FilmListItem(value.films[index], onSelectedFilm)
                            }
                        }
                    }

                    is HomeFilmState.Error -> {
                        ContentError() {
                            onRetry.invoke()
                        }
                    }

                    else -> Unit
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmCard(
    isPlaceholder: Boolean = false,
    onSelectedItem: (() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    OutlinedCard(
        onClick = { onSelectedItem?.invoke() },
        modifier = Modifier.height(LocalDimensions.current.heightCard).placeholder(
            visible = isPlaceholder,
            highlight = PlaceholderHighlight.shimmer(),
        ),
    ) {
        content?.invoke(this)
    }
}

@Composable
private fun FilmListItem(
    film: Film,
    onSelectedFilm: ((film: Film) -> Unit)? = null,
) {
    FilmCard(
        onSelectedItem = { onSelectedFilm?.invoke(film) },
    ) {
        FilmPoster(film)
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.fillMaxWidth().height(50.dp),
        ) {
            Text(
                text = film.title,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxSize().padding(4.dp),
            )
        }
    }
}

@Composable
private fun FilmPoster(
    film: Film,
) {
    AsyncImage(
        model = Builder(LocalContext.current).data("${UrlImage.BASE_IMAGE}${film.posterPath}")
            .memoryCachePolicy(CachePolicy.ENABLED).build(),
        contentDescription = "Poster",
        modifier = Modifier.height(LocalDimensions.current.imageSmall),
        contentScale = ContentScale.FillBounds,
    )
}
