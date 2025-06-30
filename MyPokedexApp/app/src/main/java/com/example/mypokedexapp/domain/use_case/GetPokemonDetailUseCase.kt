package com.example.mypokedexapp.domain.use_case

import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Result<PokemonDetail> {
        return repository.getPokemonDetail(name)
    }
}