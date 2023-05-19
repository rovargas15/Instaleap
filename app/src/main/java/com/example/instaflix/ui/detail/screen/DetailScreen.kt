package com.example.instaflix.ui.detail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.ui.common.FilmPoster
import com.example.instaflix.ui.common.UiState
import com.example.instaflix.ui.detail.state.FilmUiState
import com.example.instaflix.ui.detail.state.SeriesUiState
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel
import com.example.instaflix.ui.theme.ColorRate
import com.example.instaflix.ui.theme.LocalDimensions
import com.example.instaflix.ui.utils.Tag.BTN_BACK
import com.example.instaflix.ui.utils.Tag.ICON_STAR
import com.example.instaflix.ui.utils.Tag.IMG_BACKDROP
import com.example.instaflix.ui.utils.Tag.IMG_POSTER
import com.example.instaflix.ui.utils.UrlImage.w200
import com.example.instaflix.ui.utils.UrlImage.w400

@Composable
fun DetailScreen(
    detailFilmViewModel: DetailFilmViewModel,
    onBackPressedCallback: () -> Unit,
) {
    val filmState = detailFilmViewModel.filmState
    val seriesState = detailFilmViewModel.seriesState

    LaunchedEffect(null) {
        detailFilmViewModel.onLoad()
    }

    ContentDetail(
        filmState,
        onBackPressedCallback,
    )

    ContentDetail(
        seriesState,
        onBackPressedCallback,
    )
}

@Composable
private fun ContentDetail(
    state: UiState,
    onBackPressedCallback: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is FilmUiState -> {
                state.film?.let { DetailFilmConstraintLayout(it) }
            }

            is SeriesUiState -> {
                state.series?.let { DetailSeriesConstraintLayout(it) }
            }
        }
        IconButton(
            modifier = Modifier.testTag(BTN_BACK)
                .padding(LocalDimensions.current.paddingSmall),
            onClick = { onBackPressedCallback() },
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "ArrowBack",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun DetailFilmConstraintLayout(film: Film) {
    val scrollState = rememberScrollState()
    ConstraintLayout(modifier = Modifier.verticalScroll(scrollState)) {
        val (header, poster, title, date, voteText, voteStar, overview) = createRefs()

        FilmPoster(
            urlImage = film.backdropPath ?: "",
            size = w400,
            modifier = Modifier.testTag(IMG_BACKDROP).height(
                LocalDimensions.current.imageSmall,
            ).constrainAs(header) {},
        )

        FilmPoster(
            urlImage = film.posterPath ?: "",
            size = w200,
            modifier = Modifier.testTag(IMG_POSTER).height(
                LocalDimensions.current.imageSmall,
            ).width(LocalDimensions.current.imageXSmall).constrainAs(poster) {
                top.linkTo(header.bottom)
                bottom.linkTo(header.bottom)
                start.linkTo(header.start, 10.dp)
            },
        )

        Text(
            text = film.title,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                .constrainAs(title) {
                    start.linkTo(poster.end)
                    end.linkTo(header.end, 10.dp)
                    top.linkTo(header.bottom)
                    bottom.linkTo(poster.bottom, 50.dp)
                    width = Dimension.fillToConstraints
                },
        )

        Text(
            text = film.releaseDate.trim(),
            modifier = Modifier.constrainAs(date) {
                start.linkTo(title.start, 8.dp)
                top.linkTo(title.bottom)
            },
        )

        Text(
            text = film.voteAverage.toString(),
            modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                .constrainAs(voteText) {
                    start.linkTo(title.start)
                    top.linkTo(date.bottom)
                },
        )

        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = ColorRate,
            modifier = Modifier.testTag(ICON_STAR).constrainAs(voteStar) {
                start.linkTo(voteText.end)
                top.linkTo(voteText.top)
                bottom.linkTo(voteText.bottom)
            },
        )

        Text(
            text = film.overview,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(overview) {
                top.linkTo(poster.bottom, 10.dp)
                start.linkTo(poster.start)
                end.linkTo(header.end, 10.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
fun DetailSeriesConstraintLayout(series: Series) {
    val scrollState = rememberScrollState()
    ConstraintLayout(modifier = Modifier.verticalScroll(scrollState)) {
        val (header, poster, title, date, voteText, voteStar, overview) = createRefs()

        FilmPoster(
            urlImage = series.backdropPath ?: "",
            size = w400,
            modifier = Modifier.testTag(IMG_BACKDROP).height(
                LocalDimensions.current.imageSmall,
            ).constrainAs(header) {},
        )

        FilmPoster(
            urlImage = series.posterPath,
            size = w200,
            modifier = Modifier.testTag(IMG_POSTER).height(
                LocalDimensions.current.imageSmall,
            ).width(LocalDimensions.current.imageXSmall).constrainAs(poster) {
                top.linkTo(header.bottom)
                bottom.linkTo(header.bottom)
                start.linkTo(header.start, 10.dp)
            },
        )

        Text(
            text = series.name,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                .constrainAs(title) {
                    start.linkTo(poster.end)
                    end.linkTo(header.end, 10.dp)
                    top.linkTo(header.bottom)
                    bottom.linkTo(poster.bottom, 50.dp)
                    width = Dimension.fillToConstraints
                },
        )

        Text(
            text = series.firstAirDate.trim(),
            modifier = Modifier.constrainAs(date) {
                start.linkTo(title.start, 8.dp)
                top.linkTo(title.bottom)
            },
        )

        Text(
            text = series.voteAverage.toString(),
            modifier = Modifier.padding(start = LocalDimensions.current.paddingMedium)
                .constrainAs(voteText) {
                    start.linkTo(title.start)
                    top.linkTo(date.bottom)
                },
        )

        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = ColorRate,
            modifier = Modifier.testTag(ICON_STAR).constrainAs(voteStar) {
                start.linkTo(voteText.end)
                top.linkTo(voteText.top)
                bottom.linkTo(voteText.bottom)
            },
        )

        Text(
            text = series.overview,
            textAlign = TextAlign.Justify,
            modifier = Modifier.constrainAs(overview) {
                top.linkTo(poster.bottom, 10.dp)
                start.linkTo(poster.start)
                end.linkTo(header.end, 10.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.fillToConstraints
            },
        )
    }
}
