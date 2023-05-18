package com.example.instaflix.ui.home.state

import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.model.Series
import com.example.instaflix.ui.common.UiState

data class UpcomingUiState(
    val films: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class FilmsNowPlayingUiState(
    val films: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class PopularFilmsUiState(
    val films: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class TopRatedSeriesUiState(
    val seriesList: List<Series> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class OnTheAirSeriesUiState(
    val seriesList: List<Series> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}

data class PopularSeriesUiState(
    val seriesList: List<Series> = emptyList(),
    val isLoading: Boolean = false,
    val errorObject: Pair<Boolean, String?>? = null,
) : UiState {
    override fun isError() = errorObject != null
    override fun getErrorMessage(): String {
        return this.errorObject?.second.orEmpty()
    }
}
