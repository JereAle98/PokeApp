package com.example.pokeapi.data.response

import com.example.pokeapi.model.PokeModel
import com.google.gson.annotations.SerializedName

data class PokeResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("abilities") val abilities: List<PokeAbilitiesResponse>,
    @SerializedName("types") val types: List<PokeTypesResponse>
    ){
    fun toPresentation(): PokeModel {
            return PokeModel(
                name = name,
                url = url,
            )
        }
}
