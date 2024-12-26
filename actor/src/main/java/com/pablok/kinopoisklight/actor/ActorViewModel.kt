package com.pablok.kinopoisklight.actor

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablok.kinopoisklight.actors_repository.ActorsRepository
import com.pablok.kinopoisklight.core.dto.ActorDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ActorDetailsScreenState(
    val actor: ActorDetails? = null,
    val isRefreshing: Boolean = true,
    val errorMessage: String? = null,
)

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorRepo: ActorsRepository
) : ViewModel() {
    private val _screenState = mutableStateOf(ActorDetailsScreenState())
    val screenState: State<ActorDetailsScreenState> get() = _screenState

    fun fetch(movieId: String?) {
        Log.d("mytag", "id: $movieId")
        _screenState.value = screenState.value.copy(isRefreshing = true)
        var id = 797
        try {
            id = Integer.valueOf(movieId!!)
        }catch (_: Exception) {}

        viewModelScope.launch {
            val actor = actorRepo.getActor(id)
            _screenState.value = screenState.value.copy(
                actor = actor.actor,
                errorMessage = actor.errorMessage,
                isRefreshing = false
            )
        }
    }
}