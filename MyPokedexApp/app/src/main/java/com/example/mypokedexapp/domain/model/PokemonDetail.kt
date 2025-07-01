package com.example.mypokedexapp.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Double,
    val weight: Double,
    val imageUrl: String,
    val types: List<Type>,
    val stats: List<Stat>,
    val isFavorite: Boolean
)

data class Type(
    val name: String
)

data class Stat(
    val name: String,
    val value: Int
)