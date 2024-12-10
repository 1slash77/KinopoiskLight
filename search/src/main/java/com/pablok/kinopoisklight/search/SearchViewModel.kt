package com.pablok.kinopoisklight.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel

data class SearchScreenState(
    val total: Int? = null,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
/*    private val comicsRepository: ComicsRepository,
    appSettings: AppSettings*/
) : ViewModel() {
    private val _comicsScreenState = mutableStateOf(SearchScreenState())
    val comicsScreenState: State<SearchScreenState> get() = _comicsScreenState
}
