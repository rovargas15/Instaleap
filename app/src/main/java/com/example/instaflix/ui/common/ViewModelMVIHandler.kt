package com.example.instaflix.ui.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope

interface UiState {
    fun isError(): Boolean
    fun getErrorMessage(): String
}

abstract class ViewModelMVIHandler<state : UiState>(
    initialState: state,
    val viewModelScope: CoroutineScope,
) {

    private val _viewState: MutableState<state> = mutableStateOf(initialState)
    private val viewState: State<state> = _viewState

    abstract fun createInitialState(): UiState

    fun getState() = viewState.value

    fun setState(reducer: state.() -> state) {
        _viewState.value = viewState.value.reducer()
    }
}
