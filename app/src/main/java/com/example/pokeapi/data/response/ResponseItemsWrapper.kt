package com.example.pokeapi.data.response

import com.google.gson.annotations.SerializedName

data class ResponseItemsWrapper(
    @SerializedName("count") val countItem:Int,
    @SerializedName("previous") val prevItem:String?,
    @SerializedName("next") val nextItem:String?,
    @SerializedName("results") val resultsItem:List<ItemResponse>
)
