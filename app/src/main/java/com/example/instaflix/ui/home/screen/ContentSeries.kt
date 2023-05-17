package com.example.instaflix.ui.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instaflix.domain.model.Series
import com.example.instaflix.ui.common.FilmPoster
import com.example.instaflix.ui.home.viewmodel.HomeFilmViewModel
import com.example.instaflix.ui.theme.LocalDimensions

@Composable
fun HomeContentSeries(
    homeFilmViewModel: HomeFilmViewModel,
    snackbarHostState: SnackbarHostState,
    onSelectedItem: ((id: Long) -> Unit)?,
) {
    val popularSeriesState = homeFilmViewModel.popularSeriesState
    val onTheAirSeriesState = homeFilmViewModel.onTheAirSeriesState
    val topRatedSeriesPlayingState = homeFilmViewModel.topRatedSeriesPlayingState

    CreateCategoryFilms(popularSeriesState, snackbarHostState, onSelectedItem)
    CreateCategoryFilms(
        onTheAirSeriesState,
        snackbarHostState,
        onSelectedItem,
    )
    CreateCategoryFilms(
        topRatedSeriesPlayingState,
        snackbarHostState,
        onSelectedItem,
    )
}

@Composable
fun CategorySeries(
    categoryTitle: String,
    series: List<Series>,
    isLoading: Boolean,
    onSelectedItem: ((id: Long) -> Unit)? = null,
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

        if (isLoading) {
            LazyRow {
                items(5) {
                    FilmCard(isPlaceholder = true)
                }
            }
        } else {
            LazyRow {
                items(series) {
                    SeriesListItem(it, onSelectedItem)
                }
            }
        }
    }
}

@Composable
fun SeriesListItem(
    series: Series,
    onSelectedItem: ((id: Long) -> Unit)? = null,
) {
    FilmCard(
        onSelectedItem = { onSelectedItem?.invoke(series.id) },
    ) {
        Column {
            FilmPoster(series.posterPath ?: "")
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.fillMaxWidth().height(50.dp),
            ) {
                Text(
                    text = series.name,
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
