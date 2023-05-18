package com.example.instaflix.ui.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

interface UiState {
    fun isError(): Boolean
    fun getErrorMessage(): String
}

abstract class ViewModelMVIHandler<state : UiState>(
    initialState: state,
    val viewModelScope: CoroutineScope,
) {

    val viewState = MutableStateFlow(initialState)

    abstract fun createInitialState(): UiState

    fun setState(reducer: state.() -> state) {
        viewState.value = viewState.value.reducer()
    }
}
