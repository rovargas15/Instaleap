package com.example.instaflix.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instaflix.domain.model.Film
import com.example.instaflix.domain.usecase.GetFilmByQueryUC
import com.example.instaflix.ui.search.state.SearchFilmState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class SearchFilmViewModel @Inject constructor(
    private val getFilmByQueryUC: GetFilmByQueryUC,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _viewState = MutableLiveData<SearchFilmState>()
    val viewState: LiveData<SearchFilmState>
        get() = _viewState

    private fun getFilmByQuery(query: String) {
        getFilmByQueryUC.invoke(query).map { result ->
            result.fold(
                onSuccess = { films: List<Film> ->
                    _viewState.postValue(SearchFilmState.Success(films))
                },
                onFailure = {
                    _viewState.postValue(SearchFilmState.Error)
                },
            )
        }.onStart {
            _viewState.postValue(SearchFilmState.Loader)
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}
