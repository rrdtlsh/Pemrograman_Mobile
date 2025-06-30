package com.example.mypokedexapp.domain.use_case

import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<Result<List<Pokemon>>> { // <-- Pastikan tipenya seperti ini
        return repository.getPokemonList()
    }
}