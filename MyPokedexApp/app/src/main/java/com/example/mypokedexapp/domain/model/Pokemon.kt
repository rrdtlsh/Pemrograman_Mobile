package com.example.mypokedexapp.domain.model

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean = false // <-- PERBAIKAN: Tambahkan ini
)