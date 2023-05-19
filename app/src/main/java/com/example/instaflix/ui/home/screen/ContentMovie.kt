package com.example.instaflix.ui.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instaflix.domain.model.Film
import com.example.instaflix.ui.common.ContentError
import com.example.instaflix.ui.common.FilmPoster
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.ui.theme.LocalDimensions

@Composable
fun HomeContentMovie(
    homeFilmViewModel: HomeFilmViewModel,
    onSelectedItem: ((id: Long) -> Unit)?,
    onRetry: (() -> Unit),
) {
    val upcomingState by homeFilmViewModel.upcomingFilmState.collectAsState()
    val popularFilmsState by homeFilmViewModel.popularFilmsState.collectAsState()

    CreateCategoryItem(
        state = upcomingState,
        onSelectedItem = onSelectedItem,
        onRetry = onRetry,
    )
    CreateCategoryItem(
        state = popularFilmsState,
        onSelectedItem = onSelectedItem,
        onRetry = onRetry,
    )
}

@Composable
fun FilmCategoryItem(
    categoryTitle: String,
    isLoading: Boolean,
    films: List<Film>,
    isError: Boolean,
    onSelectedItem: ((id: Long) -> Unit)? = null,
    onRetry: (() -> Unit),
) {
    Column(
        modifier = Modifier.padding(
            start = LocalDimensions.current.paddingSmall,
            end = LocalDimensions.current.paddingSmall,
        ),
    ) {
        Text(
            categoryTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium),
        )

        when {
            isError -> {
                ContentError(onRetry)
            }

            isLoading -> {
                LazyRow {
                    items(5) {
                        CardItem(isPlaceholder = true)
                    }
                }
            }

            else -> {
                LazyRow(
                    state = rememberLazyListState(),
                ) {
                    items(films) {
                        FilmListItem(it, onSelectedItem)
                    }
                }
            }
        }
    }
}

@Composable
fun FilmListItem(
    film: Film,
    onSelectedItem: ((id: Long) -> Unit)? = null,
) {
    CardItem(
        onSelectedItem = { onSelectedItem?.invoke(film.id) },
    ) {
        Column {
            FilmPoster(film.posterPath ?: "")
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.fillMaxWidth().height(50.dp),
            ) {
                Text(
                    text = film.title,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                        .padding(LocalDimensions.current.paddingSmall),
                )
            }
        }
    }
}
