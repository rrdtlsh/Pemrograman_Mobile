package com.example.mypokedexapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// PERBAIKAN: Tambahkan anotasi @JsonClass untuk membantu Moshi
@JsonClass(generateAdapter = true)
data class PokemonDetailDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "height") val height: Int?, // Bisa null
    @field:Json(name = "weight") val weight: Int?, // Bisa null
    @field:Json(name = "stats") val stats: List<StatDto>,
    @field:Json(name = "types") val types: List<TypeDto>,
    @field:Json(name = "sprites") val sprites: SpritesDto
)

@JsonClass(generateAdapter = true)
data class StatDto(
    @field:Json(name = "base_stat") val baseStat: Int?, // Bisa null
    @field:Json(name = "stat") val stat: StatInfoDto?
)

@JsonClass(generateAdapter = true)
data class StatInfoDto(
    @field:Json(name = "name") val name: String?
)

@JsonClass(generateAdapter = true)
data class TypeDto(
    @field:Json(name = "type") val type: TypeInfoDto
)

@JsonClass(generateAdapter = true)
data class TypeInfoDto(
    @field:Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class SpritesDto(
    @field:Json(name = "front_default") val frontDefault: String?, // Bisa null
    // PERBAIKAN: Tambahkan referensi ke gambar lain yang lebih baik
    @field:Json(name = "other") val other: OtherSpritesDto?
)

@JsonClass(generateAdapter = true)
data class OtherSpritesDto(
    @field:Json(name = "official-artwork") val officialArtwork: OfficialArtworkDto?
)

@JsonClass(generateAdapter = true)
data class OfficialArtworkDto(
    @field:Json(name = "front_default") val frontDefault: String?
)