package com.example.mypokedexapp.ui.screens.pokemon_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.use_case.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonDetailState(
    val pokemon: PokemonDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PokemonDetailState())
    val state: State<PokemonDetailState> = _state

    init {
        savedStateHandle.get<String>("pokemonName")?.let { name ->
            getPokemonDetail(name)
        }
    }

    private fun getPokemonDetail(name: String) {
        viewModelScope.launch {
            _state.value = PokemonDetailState(isLoading = true)
            getPokemonDetailUseCase(name)
                .onSuccess { pokemon ->
                    _state.value = PokemonDetailState(pokemon = pokemon)
                }
                .onFailure { error ->
                    _state.value = PokemonDetailState(error = error.localizedMessage ?: "An unknown error occurred")
                }
        }
    }
}