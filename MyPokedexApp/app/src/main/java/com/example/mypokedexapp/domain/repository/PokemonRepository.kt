package com.example.mypokedexapp.domain.repository

import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<Result<List<Pokemon>>>
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}