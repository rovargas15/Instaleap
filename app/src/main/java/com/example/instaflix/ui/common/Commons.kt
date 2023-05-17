package com.example.instaflix.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.instaflix.R
import com.example.instaflix.ui.theme.LocalDimensions
import com.example.instaflix.ui.utils.UrlImage
import com.example.instaflix.ui.utils.UrlImage.w300

@Composable
fun CreateAnimation(
    raw: LottieCompositionSpec.RawRes,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(spec = raw)
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.size(LocalDimensions.current.imageXSmall),
    )
}

@Composable
fun ContentError(onRetry: (() -> Unit)) {
    Column(
        modifier = Modifier.fillMaxSize().testTag("ContentError"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            LocalDimensions.current.paddingSmall,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        CreateAnimation(raw = LottieCompositionSpec.RawRes(R.raw.error))
        Text(
            text = stringResource(id = R.string.message_error),
        )
        TextButton(
            onClick = {
                onRetry.invoke()
            },
        ) {
            Text(
                text = stringResource(id = R.string.btn_retry),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun FilmPoster(
    urlImage: String,
    size: String = w300,
    modifier: Modifier = Modifier.height(LocalDimensions.current.imageSmall),
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${UrlImage.BASE_IMAGE}/$size/$urlImage").memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = "Poster",
        contentScale = ContentScale.FillBounds,
        modifier = modifier,
    )
}
