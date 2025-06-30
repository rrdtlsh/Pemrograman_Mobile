package com.example.mypokedexapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypokedexapp.domain.model.Pokemon

@Entity(tableName = "pokemon_list")
data class PokemonEntity(
    @PrimaryKey val name: String,
    val url: String
) {
    fun toDomain(): Pokemon {
        val number = url.split("/").dropLast(1).last()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
        return Pokemon(
            name = this.name,
            imageUrl = imageUrl
        )
    }
}