package com.example.mypokedexapp.ui.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.domain.use_case.GetPokemonListUseCase
import com.example.mypokedexapp.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<PokemonListUiState> = getPokemonListUseCase()
        .combine(_searchQuery) { result, query ->
            result.fold(
                onSuccess = { pokemonList ->
                    val filteredList = if (query.isBlank()) {
                        pokemonList
                    } else {
                        pokemonList.filter {
                            it.name.contains(query, ignoreCase = true)
                        }
                    }
                    PokemonListUiState(
                        pokemonList = filteredList,
                        searchQuery = query
                    )
                },
                onFailure = { exception ->
                    PokemonListUiState(
                        error = exception.localizedMessage ?: "Terjadi kesalahan",
                        searchQuery = query
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PokemonListUiState(isLoading = true)
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFavoriteClick(pokemon: Pokemon) {
        viewModelScope.launch {
            toggleFavoriteUseCase(pokemon.name, !pokemon.isFavorite)
        }
    }
}