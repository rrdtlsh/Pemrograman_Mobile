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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    // PERBAIKAN: Mengubah return type dan membungkus setiap emit dengan Result
    override fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {
        // 1. Emit data dari database dulu sebagai data awal
        val localPokemonList = dao.getPokemonList().first().map { it.toDomain() }
        emit(Result.success(localPokemonList))

        // 2. Fetch data dari API
        try {
            val remotePokemonList = api.getPokemonList(limit = 151, offset = 0)
            dao.clearPokemonList()
            dao.insertPokemonList(
                remotePokemonList.results.map { PokemonEntity(it.name, it.url) }
            )
            // Emit data baru yang sukses dari API
            val newLocalPokemonList = dao.getPokemonList().first().map { it.toDomain() }
            emit(Result.success(newLocalPokemonList))

        } catch(e: HttpException) {
            Log.e("RepoImpl", "HttpException", e)
            // Emit kegagalan jika ada error HTTP
            emit(Result.failure(e))
        } catch(e: IOException) {
            Log.e("RepoImpl", "IOException", e)
            // Emit kegagalan jika tidak ada internet
            emit(Result.failure(e))
        }
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        try {
            val localDetail = dao.getPokemonDetail(name)
            if (localDetail != null) {
                return Result.success(localDetail.toDomain())
            }

            val remoteDetail = api.getPokemonDetail(name)
            val detailEntity = PokemonDetailEntity(
                id = remoteDetail.id,
                name = remoteDetail.name,
                height = remoteDetail.height,
                weight = remoteDetail.weight,
                imageUrl = remoteDetail.sprites.frontDefault ?: "",
                types = remoteDetail.types.map { it.type.name },
                stats = remoteDetail.stats.mapNotNull { statDto ->
                    statDto.stat?.name?.let { statName ->
                        StatEmbeddable(statName, statDto.baseStat ?: 0)
                    }
                }
            )
            dao.insertPokemonDetail(detailEntity)
            return Result.success(detailEntity.toDomain())

        } catch (e: Exception) {
            Log.e("RepoImpl", "getPokemonDetail Exception", e)
            return Result.failure(e)
        }
    }
}