package com.example.instaflix.ui.detail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.instaflix.R
import com.example.instaflix.ui.common.ContentError
import com.example.instaflix.ui.detail.state.DetailFilmState
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel

@Composable
fun DetailScreen(
    detailFilmViewModel: DetailFilmViewModel,
    onBackPressedCallback: () -> Unit,
) {
    val state = detailFilmViewModel.viewState.observeAsState()
    ContentFilmDetail(
        state,
        onBackPressedCallback,
    ) {
        // TODO: retry
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppTopBar(onBackPressedCallback: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                LocalContext.current.getString(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressedCallback() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun ContentFilmDetail(
    state: State<DetailFilmState?>,
    onBackPressedCallback: () -> Unit,
    onRetry: (() -> Unit),
) {
    Scaffold(
        topBar = { AppTopBar(onBackPressedCallback) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (val value = state.value) {
                    is DetailFilmState.Loader -> {
                        // TODO: implementar
                    }

                    is DetailFilmState.Success -> {
                        Text(text = value.film.title)
                    }

                    is DetailFilmState.Error -> {
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
