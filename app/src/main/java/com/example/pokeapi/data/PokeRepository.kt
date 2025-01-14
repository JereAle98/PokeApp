package com.example.pokeapi.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokeapi.data.response.FormResponse
import com.example.pokeapi.data.response.ImageResponse
import com.example.pokeapi.data.response.PokeDetailResponse
import com.example.pokeapi.data.response.PokeResponse
import com.example.pokeapi.model.PokeModel
import com.example.pokeapi.model.PokeWrapperModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokeRepository @Inject constructor(val api: PokeApiService) {

    companion object{
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 3
    }

    fun getAllPokemons(): Flow<PagingData<PokeModel>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                PokePagingSource(api)
            }).flow
    }

    suspend fun getPokemonByName(name: String): PokeDetailResponse? {
        val response = api.getDetailPokemon(name)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun getPokemonImage(name: String): FormResponse? {
        val response = api.getPokemonImage(name)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

}