package com.example.mypokedexapp.data.local.dao

import androidx.room.*
import com.example.mypokedexapp.data.local.entity.PokemonDetailEntity
import com.example.mypokedexapp.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    // For Pokemon List
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_list")
    fun getPokemonList(): Flow<List<PokemonEntity>>

    @Query("DELETE FROM pokemon_list")
    suspend fun clearPokemonList()

    // For Pokemon Detail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(detail: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    suspend fun getPokemonDetail(name: String): PokemonDetailEntity?
}