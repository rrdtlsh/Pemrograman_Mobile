package com.example.mypokedexapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.mypokedexapp.data.local.PokemonDatabase
import com.example.mypokedexapp.data.local.dao.PokemonDao
import com.example.mypokedexapp.data.remote.PokeApiService
import com.example.mypokedexapp.data.repository.PokemonRepositoryImpl
import com.example.mypokedexapp.domain.repository.PokemonRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePokeApiService(moshi: Moshi): PokeApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(app: Application): PokemonDatabase {
        return Room.databaseBuilder(
            app,
            PokemonDatabase::class.java,
            "pokemon_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(db: PokemonDatabase): PokemonDao {
        return db.pokemonDao()
    }

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApiService, dao: PokemonDao): PokemonRepository {
        return PokemonRepositoryImpl(api, dao)
    }
}