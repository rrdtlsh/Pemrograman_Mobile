package com.example.mypokedexapp.data.remote.dto

import com.squareup.moshi.Json

data class PokemonDetailDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "weight") val weight: Int,
    @field:Json(name = "stats") val stats: List<StatDto>,
    @field:Json(name = "types") val types: List<TypeDto>,
    @field:Json(name = "sprites") val sprites: SpritesDto
)

data class StatDto(
    @field:Json(name = "base_stat") val baseStat: Int?,
    @field:Json(name = "stat") val stat: StatInfo_Dto?
)

data class StatInfo_Dto(
    @field:Json(name = "name") val name: String
)

data class TypeDto(
    @field:Json(name = "type") val type: TypeInfo_Dto
)

data class TypeInfo_Dto(
    @field:Json(name = "name") val name: String
)

data class SpritesDto(
    @field:Json(name = "front_default") val frontDefault: String?
)