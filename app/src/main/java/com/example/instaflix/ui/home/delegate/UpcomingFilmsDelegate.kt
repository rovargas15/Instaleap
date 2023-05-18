package com.example.instaflix.ui.home.delegate

import com.example.instaflix.domain.usecase.GetFilmsByCategoryUC
import com.example.instaflix.ui.common.ViewModelMVIHandler
import com.example.instaflix.ui.home.state.UpcomingUiState
import com.example.instaflix.ui.utils.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpcomingFilmsDelegate(
    initialState: UpcomingUiState,
    private val getFilmsByCategoryUC: GetFilmsByCategoryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
    viewModelScope: CoroutineScope,
) : ViewModelMVIHandler<UpcomingUiState>(initialState, viewModelScope) {

    override fun createInitialState() = UpcomingUiState(isLoading = true)

    fun fetchData() {
        setState { UpcomingUiState().copy(isLoading = true) }

        viewModelScope.launch(coroutineDispatcher) {
            getFilmsByCategoryUC.invoke(Category.UPCOMING).fold(
                onSuccess = { filmResult ->
                    setState { copy(films = filmResult, isLoading = false) }
                },
                onFailure = { error ->
                    setState {
                        copy(
                            isLoading = false,
                            errorObject = Pair(true, error.message),
                        )
                    }
                },
            )
        }
    }
}
