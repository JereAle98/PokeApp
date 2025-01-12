package com.example.pokeapi.data.response

import com.example.pokeapi.model.ItemModel
import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("name") val name: String
){
    fun toItemPresentation(): ItemModel {
    return ItemModel(
        name = name
    )
    }
}