package com.example.mypokedexapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.model.Stat
import com.example.mypokedexapp.domain.model.Type

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int, // in decimetres
    val weight: Int, // in hectograms
    val imageUrl: String,
    val types: List<String>,
    val stats: List<StatEmbeddable>,
    var isFavorite: Boolean = false
) {
    fun toDomain(): PokemonDetail {
        return PokemonDetail(
            id = this.id,
            name = this.name,
            height = this.height / 10.0, // Convert to meters
            weight = this.weight / 10.0, // Convert to kg
            imageUrl = this.imageUrl,
            types = this.types.map { Type(name = it) },
            stats = this.stats.map { Stat(name = it.name, value = it.value) }
        )
    }
}

data class StatEmbeddable(
    val name: String,
    val value: Int
)