package com.example.instaflix.ui.detail.state

import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.ui.common.UiState

data class FilmUiState(
    val film: Film? = null,
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class SeriesUiState(
    val series: Series? = null,
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}
