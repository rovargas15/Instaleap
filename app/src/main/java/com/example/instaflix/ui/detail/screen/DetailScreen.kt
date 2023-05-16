package com.example.instaflix.ui.detail.screen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.instaflix.ui.detail.state.DetailFilmState
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel

@Composable
fun DetailScreen(
    detailFilmViewModel: DetailFilmViewModel,
    onBackPressedCallback: () -> Unit,
) {
    val state = detailFilmViewModel.viewState.observeAsState()
    when (val value = state.value) {
        is DetailFilmState.Loader -> {
        }

        is DetailFilmState.Success -> {
            Text(text = value.film.title)
        }

        is DetailFilmState.Error -> {
            Log.e("state", "Error")
        }

        else -> Unit
    }
}
