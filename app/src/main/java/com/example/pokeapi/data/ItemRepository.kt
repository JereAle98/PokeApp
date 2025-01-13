package com.example.pokeapi.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokeapi.data.response.PokeDetailResponse
import com.example.pokeapi.model.ItemModel
import com.example.pokeapi.model.PokeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(val api: PokeApiService) {

    companion object{
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 3
    }

    fun getAllItems(): Flow<PagingData<ItemModel>> {
        return Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                ItemPagingSource(api)
            }).flow

    }

//    suspend fun getPokemonByName(name: String): PokeDetailResponse? {
//        val response = api.getDetailPokemon(name)
//        if (response.isSuccessful){
//            return response.body()
//        }
//        return null
//    }

}