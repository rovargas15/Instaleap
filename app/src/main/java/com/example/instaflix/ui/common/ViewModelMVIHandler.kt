package com.example.instaflix.ui.common

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UiState {
    fun isError(): Boolean
    fun getErrorMessage(): String
}

abstract class ViewModelMVIHandler<state : UiState>(
    initialState: state,
    val viewModelScope: CoroutineScope,
) {
    private val _viewState = MutableStateFlow(initialState)
    private val viewState: StateFlow<state> = _viewState

    abstract fun createInitialState(): UiState

    @VisibleForTesting
    internal fun getMutableState() = _viewState

    fun getState(): state = viewState.value

    fun setState(reducer: state.() -> state) {
        _viewState.value = viewState.value.reducer()
    }
}
