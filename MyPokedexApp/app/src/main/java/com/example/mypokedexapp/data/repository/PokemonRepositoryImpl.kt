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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    // Fungsi ini sudah benar
    override fun getPokemonList(): Flow<Result<List<Pokemon>>> {
        // Gabungkan Flow dari list utama dan Flow dari list favorit
        return combine(
            dao.getPokemonList(),
            dao.getFavoritePokemons()
        ) { pokemonEntities, favoriteEntities ->
            // Buat Set berisi nama pokemon favorit untuk pengecekan cepat
            val favoriteNames = favoriteEntities.map { it.name }.toSet()

            // Map data dari database ke domain model, sambil mengisi status favorit
            pokemonEntities.map { entity ->
                entity.toDomain().copy(
                    isFavorite = entity.name in favoriteNames
                )
            }
        }.flowOn(Dispatchers.IO) // Pastikan mapping terjadi di background thread
            .map<List<Pokemon>, Result<List<Pokemon>>> { Result.success(it) }
            .onStart {
                // Logika untuk fetch dari API tetap sama, tapi sekarang tidak langsung emit
                try {
                    val remotePokemonList = api.getPokemonList(limit = 151, offset = 0)
                    dao.clearPokemonList()
                    dao.insertPokemonList(
                        remotePokemonList.results.map { PokemonEntity(it.name, it.url) }
                    )
                } catch (e: Exception) {
                    Log.e("RepoImpl", "Failed to fetch from API, using local data.", e)
                    // Jika API gagal, kita tidak perlu emit failure, karena Flow akan tetap
                    // menyediakan data dari cache. Cukup log errornya.
                }
            }
    }

    // Fungsi ini sudah benar
    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        try {
            val localDetail = dao.getPokemonDetail(name)
            val remoteDetail = api.getPokemonDetail(name)
            val imageUrl = remoteDetail.sprites.other?.officialArtwork?.frontDefault
                ?: remoteDetail.sprites.frontDefault
                ?: ""

            val detailEntity = PokemonDetailEntity(
                id = remoteDetail.id,
                name = remoteDetail.name,
                height = remoteDetail.height ?: 0,
                weight = remoteDetail.weight ?: 0,
                imageUrl = imageUrl,
                types = remoteDetail.types.map { it.type.name },
                stats = remoteDetail.stats.mapNotNull { statDto ->
                    statDto.stat?.name?.let { statName ->
                        StatEmbeddable(statName, statDto.baseStat ?: 0)
                    }
                },
                isFavorite = localDetail?.isFavorite ?: false
            )
            dao.insertPokemonDetail(detailEntity)
            return Result.success(detailEntity.toDomain())

        } catch (e: Exception) {
            Log.e("RepoImpl", "getPokemonDetail Exception", e)
            val cachedDetail = dao.getPokemonDetail(name)
            return if (cachedDetail != null) {
                Result.success(cachedDetail.toDomain())
            } else {
                Result.failure(e)
            }
        }
    }

    // PERBAIKAN: Menambahkan implementasi untuk getFavoritePokemons
    override fun getFavoritePokemons(): Flow<List<PokemonDetail>> {
        return dao.getFavoritePokemons().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    // PERBAIKAN: Menambahkan implementasi untuk toggleFavorite
    override suspend fun toggleFavorite(pokemonName: String, isFavorite: Boolean) {
        dao.setFavorite(pokemonName, isFavorite)
    }
}