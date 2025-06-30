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
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val types: List<String>,
    val stats: List<StatEmbeddable>,
    var isFavorite: Boolean = false
) {
    fun toDomain(): PokemonDetail {
        return PokemonDetail(
            id = this.id,
            name = this.name,
            height = this.height / 10.0,
            weight = this.weight / 10.0,
            imageUrl = this.imageUrl,
            types = this.types.map { Type(name = it) },
            stats = this.stats.map { Stat(name = it.name, value = it.value) },
            // PERBAIKAN: Tambahkan baris ini untuk meneruskan status favorit
            isFavorite = this.isFavorite
        )
    }
}

data class StatEmbeddable(
    val name: String,
    val value: Int
)