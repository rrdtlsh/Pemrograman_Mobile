package com.example.mypokedexapp.domain.model

import androidx.compose.ui.graphics.Color

enum class PokemonType(val color: Color) {
    NORMAL(Color(0xFFA8A77A)),
    FIRE(Color(0xFFEE8130)),
    WATER(Color(0xFF6390F0)),
    ELECTRIC(Color(0xFFF7D02C)),
    GRASS(Color(0xFF7AC74C)),
    ICE(Color(0xFF96D9D6)),
    FIGHTING(Color(0xFFC22E28)),
    POISON(Color(0xFFA33EA1)),
    GROUND(Color(0xFFE2BF65)),
    FLYING(Color(0xFFA98FF3)),
    PSYCHIC(Color(0xFFF95587)),
    BUG(Color(0xFFA6B91A)),
    ROCK(Color(0xFFB6A136)),
    GHOST(Color(0xFF735797)),
    DRAGON(Color(0xFF6F35FC)),
    DARK(Color(0xFF705746)),
    STEEL(Color(0xFFB7B7CE)),
    FAIRY(Color(0xFFD685AD))
}

object TypeChart {
    val effectiveness: Map<PokemonType, Map<PokemonType, Float>> = mapOf(
        PokemonType.NORMAL to mapOf(PokemonType.ROCK to 0.5f, PokemonType.GHOST to 0f, PokemonType.STEEL to 0.5f),
        PokemonType.FIRE to mapOf(PokemonType.FIRE to 0.5f, PokemonType.WATER to 0.5f, PokemonType.GRASS to 2f, PokemonType.ICE to 2f, PokemonType.BUG to 2f, PokemonType.ROCK to 0.5f, PokemonType.DRAGON to 0.5f, PokemonType.STEEL to 2f),
        PokemonType.WATER to mapOf(PokemonType.FIRE to 2f, PokemonType.WATER to 0.5f, PokemonType.GRASS to 0.5f, PokemonType.GROUND to 2f, PokemonType.ROCK to 2f, PokemonType.DRAGON to 0.5f),
        PokemonType.ELECTRIC to mapOf(PokemonType.WATER to 2f, PokemonType.ELECTRIC to 0.5f, PokemonType.GRASS to 0.5f, PokemonType.GROUND to 0f, PokemonType.FLYING to 2f, PokemonType.DRAGON to 0.5f),
        PokemonType.GRASS to mapOf(PokemonType.FIRE to 0.5f, PokemonType.WATER to 2f, PokemonType.GRASS to 0.5f, PokemonType.POISON to 0.5f, PokemonType.GROUND to 2f, PokemonType.FLYING to 0.5f, PokemonType.BUG to 0.5f, PokemonType.ROCK to 2f, PokemonType.DRAGON to 0.5f, PokemonType.STEEL to 0.5f),
        PokemonType.ICE to mapOf(PokemonType.FIRE to 0.5f, PokemonType.WATER to 0.5f, PokemonType.GRASS to 2f, PokemonType.ICE to 0.5f, PokemonType.GROUND to 2f, PokemonType.FLYING to 2f, PokemonType.DRAGON to 2f, PokemonType.STEEL to 0.5f),
        PokemonType.FIGHTING to mapOf(PokemonType.NORMAL to 2f, PokemonType.ICE to 2f, PokemonType.POISON to 0.5f, PokemonType.FLYING to 0.5f, PokemonType.PSYCHIC to 0.5f, PokemonType.BUG to 0.5f, PokemonType.ROCK to 2f, PokemonType.GHOST to 0f, PokemonType.DARK to 2f, PokemonType.STEEL to 2f, PokemonType.FAIRY to 0.5f),
        PokemonType.POISON to mapOf(PokemonType.GRASS to 2f, PokemonType.POISON to 0.5f, PokemonType.GROUND to 0.5f, PokemonType.ROCK to 0.5f, PokemonType.GHOST to 0.5f, PokemonType.STEEL to 0f, PokemonType.FAIRY to 2f),
        PokemonType.GROUND to mapOf(PokemonType.FIRE to 2f, PokemonType.ELECTRIC to 2f, PokemonType.GRASS to 0.5f, PokemonType.POISON to 2f, PokemonType.FLYING to 0f, PokemonType.BUG to 0.5f, PokemonType.ROCK to 2f, PokemonType.STEEL to 2f),
        PokemonType.FLYING to mapOf(PokemonType.ELECTRIC to 0.5f, PokemonType.GRASS to 2f, PokemonType.FIGHTING to 2f, PokemonType.BUG to 2f, PokemonType.ROCK to 0.5f, PokemonType.STEEL to 0.5f),
        PokemonType.PSYCHIC to mapOf(PokemonType.FIGHTING to 2f, PokemonType.POISON to 2f, PokemonType.PSYCHIC to 0.5f, PokemonType.DARK to 0f, PokemonType.STEEL to 0.5f),
        PokemonType.BUG to mapOf(PokemonType.FIRE to 0.5f, PokemonType.GRASS to 2f, PokemonType.FIGHTING to 0.5f, PokemonType.POISON to 0.5f, PokemonType.FLYING to 0.5f, PokemonType.PSYCHIC to 2f, PokemonType.GHOST to 0.5f, PokemonType.DARK to 2f, PokemonType.STEEL to 0.5f, PokemonType.FAIRY to 0.5f),
        PokemonType.ROCK to mapOf(PokemonType.FIRE to 2f, PokemonType.ICE to 2f, PokemonType.FIGHTING to 0.5f, PokemonType.GROUND to 0.5f, PokemonType.FLYING to 2f, PokemonType.BUG to 2f, PokemonType.STEEL to 0.5f),
        PokemonType.GHOST to mapOf(PokemonType.NORMAL to 0f, PokemonType.PSYCHIC to 2f, PokemonType.GHOST to 2f, PokemonType.DARK to 0.5f),
        PokemonType.DRAGON to mapOf(PokemonType.DRAGON to 2f, PokemonType.STEEL to 0.5f, PokemonType.FAIRY to 0f),
        PokemonType.DARK to mapOf(PokemonType.FIGHTING to 0.5f, PokemonType.PSYCHIC to 2f, PokemonType.GHOST to 2f, PokemonType.DARK to 0.5f, PokemonType.FAIRY to 0.5f),
        PokemonType.STEEL to mapOf(PokemonType.FIRE to 0.5f, PokemonType.WATER to 0.5f, PokemonType.ELECTRIC to 0.5f, PokemonType.ICE to 2f, PokemonType.ROCK to 2f, PokemonType.STEEL to 0.5f, PokemonType.FAIRY to 2f),
        PokemonType.FAIRY to mapOf(PokemonType.FIRE to 0.5f, PokemonType.FIGHTING to 2f, PokemonType.POISON to 0.5f, PokemonType.DRAGON to 2f, PokemonType.DARK to 2f, PokemonType.STEEL to 0.5f)
    )
}