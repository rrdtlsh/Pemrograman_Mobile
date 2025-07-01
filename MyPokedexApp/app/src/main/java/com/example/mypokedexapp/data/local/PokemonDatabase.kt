package com.example.mypokedexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.mypokedexapp.data.local.dao.PokemonDao
import com.example.mypokedexapp.data.local.entity.PokemonDetailEntity
import com.example.mypokedexapp.data.local.entity.PokemonEntity
import com.example.mypokedexapp.data.local.entity.StatEmbeddable

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStatList(value: List<StatEmbeddable>): String = Gson().toJson(value)

    @TypeConverter
    fun toStatList(value: String): List<StatEmbeddable> {
        val listType = object : TypeToken<List<StatEmbeddable>>() {}.type
        return Gson().fromJson(value, listType)
    }
}


@Database(
    entities = [PokemonEntity::class, PokemonDetailEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}