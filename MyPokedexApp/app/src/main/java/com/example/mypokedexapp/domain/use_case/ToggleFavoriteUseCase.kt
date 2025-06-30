package com.example.mypokedexapp.domain.use_case

import com.example.mypokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonName: String, isFavorite: Boolean) {
        repository.toggleFavorite(pokemonName, isFavorite)
    }
}