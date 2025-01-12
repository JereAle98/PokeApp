package com.example.pokeapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapi.model.ItemModel
import java.io.IOException
import javax.inject.Inject

class ItemPagingSource @Inject constructor(private val api: PokeApiService) :
    PagingSource<Int, ItemModel>() {

    override fun getRefreshKey(state: PagingState<Int, ItemModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemModel> {

        return try {
            val page = params.key ?: 0
            val response = api.getItem(page)
            val items = response.resultsItem

            val prevKey = if (page > 0) page - 20 else null
            val nextKey = if (response.nextItem != null) page + 20 else null

            LoadResult.Page(
                data = items.map { it.toItemPresentation() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}