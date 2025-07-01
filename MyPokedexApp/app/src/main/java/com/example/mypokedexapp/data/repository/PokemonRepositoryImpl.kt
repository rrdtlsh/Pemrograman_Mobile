package com.example.mypokedexapp.data.repository

import android.util.Log
import com.example.mypokedexapp.data.local.dao.PokemonDao
import com.example.mypokedexapp.data.local.entity.PokemonDetailEntity
import com.example.mypokedexapp.data.local.entity.PokemonEntity
import com.example.mypokedexapp.data.local.entity.StatEmbeddable
import com.example.mypokedexapp.data.remote.PokeApiService
import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> {
        val localListFlow = dao.getPokemonList()
        val favoritesFlow = dao.getFavoritePokemons()

        return combine(localListFlow, favoritesFlow) { pokemonEntities, favoriteEntities ->
            val favoriteNames = favoriteEntities.map { it.name }.toSet()
            pokemonEntities.map { entity ->
                entity.toDomain().copy(
                    isFavorite = entity.name in favoriteNames
                )
            }
        }.flowOn(Dispatchers.IO)
            .map<List<Pokemon>, Result<List<Pokemon>>> { Result.success(it) }
            .onStart {
                try {
                    val remotePokemonList = api.getPokemonList(limit = 151, offset = 0)
                    dao.clearPokemonList()
                    dao.insertPokemonList(
                        remotePokemonList.results.map { PokemonEntity(it.name, it.url) }
                    )
                } catch (e: Exception) {
                    Log.e("RepoImpl", "Failed to fetch API list, using local data.", e)
                }
            }
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        val logTag = "PokemonRepo"
        try {
            Log.d(logTag, "Mencoba mengambil detail untuk: $name")
            val localDetail = dao.getPokemonDetail(name)

            val remoteDetail = api.getPokemonDetail(name)
            Log.d(logTag, "Data dari API (mentah): $remoteDetail")

            val imageUrl = remoteDetail.sprites.other?.officialArtwork?.frontDefault
                ?: remoteDetail.sprites.frontDefault
                ?: ""
            Log.d(logTag, "URL Gambar yang didapat: $imageUrl")

            val stats = remoteDetail.stats.mapNotNull { statDto ->
                statDto.stat?.name?.let { statName ->
                    StatEmbeddable(statName, statDto.baseStat ?: 0)
                }
            }
            Log.d(logTag, "Statistik yang diproses: $stats")

            val detailEntity = PokemonDetailEntity(
                id = remoteDetail.id,
                name = remoteDetail.name,
                height = remoteDetail.height ?: 0,
                weight = remoteDetail.weight ?: 0,
                imageUrl = imageUrl,
                types = remoteDetail.types.map { it.type.name },
                stats = stats,
                isFavorite = localDetail?.isFavorite ?: false
            )

            Log.d(logTag, "Entity yang akan disimpan: $detailEntity")
            dao.insertPokemonDetail(detailEntity)

            return Result.success(detailEntity.toDomain())

        } catch (e: Exception) {
            Log.e(logTag, "Gagal mengambil dari API, mencoba cache.", e)
            val cachedDetail = dao.getPokemonDetail(name)
            return if (cachedDetail != null) {
                Log.d(logTag, "Cache ditemukan: $cachedDetail")
                Result.success(cachedDetail.toDomain())
            } else {
                Log.e(logTag, "Cache tidak ditemukan untuk $name.")
                Result.failure(e)
            }
        }
    }

    override fun getFavoritePokemons(): Flow<List<PokemonDetail>> {
        return dao.getFavoritePokemons().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(pokemonName: String, isFavorite: Boolean) {
        dao.setFavorite(pokemonName, isFavorite)
    }
}