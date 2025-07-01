package com.example.mypokedexapp.domain.use_case

import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<List<PokemonDetail>> {
        return repository.getFavoritePokemons()
    }
}