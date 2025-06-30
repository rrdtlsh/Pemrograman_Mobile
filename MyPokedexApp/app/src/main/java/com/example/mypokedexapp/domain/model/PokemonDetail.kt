package com.example.mypokedexapp.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Double, // in meters
    val weight: Double, // in kg
    val imageUrl: String,
    val types: List<Type>,
    val stats: List<Stat>
)

data class Type(
    val name: String
)

data class Stat(
    val name: String,
    val value: Int
)