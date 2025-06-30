package com.example.mypokedexapp.ui.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.domain.use_case.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            // PERBAIKAN: Menggunakan logika yang benar untuk menangani Result
            getPokemonListUseCase().collect { result ->
                if (result.isSuccess) {
                    // Jika berhasil, ambil datanya dengan aman
                    val pokemonList = result.getOrNull()
                    if (pokemonList != null) {
                        _uiState.update {
                            it.copy(
                                pokemonList = pokemonList,
                                isLoading = false,
                                error = null // Hapus error lama jika sukses
                            )
                        }
                    }
                } else { // Ini berarti result.isFailure
                    // Jika gagal, ambil exception-nya dengan aman
                    val exception = result.exceptionOrNull()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception?.localizedMessage ?: "Terjadi kesalahan tidak dikenal"
                        )
                    }
                }
            }
        }
    }
}