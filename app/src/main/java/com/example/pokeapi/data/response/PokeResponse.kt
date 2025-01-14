package com.example.pokeapi.data.response

import com.example.pokeapi.model.PokeModel
import com.google.gson.annotations.SerializedName

data class PokeResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
    ){
    fun toPresentation(): PokeModel {
            return PokeModel(
                name = name,
                url = url
            )
        }
}

data class PokeDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("abilities") val abilities: List<PokeAbilitiesResponse>,
    @SerializedName("types") val types: List<PokeTypesResponse>
)

//Abilities
data class PokeAbilitiesResponse (
    @SerializedName("ability") val ability: PokeAbilityResponse,
    @SerializedName("slot") val slot: Int
)


data class PokeAbilityResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

//Types
data class PokeTypesResponse(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeResponse
)

data class TypeResponse (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class FormResponse(
    @SerializedName("sprites") val sprites: ImageResponse
)

data class ImageResponse(
    @SerializedName("front_default") val imageFront: String,
    @SerializedName("back_default") val imageBack: String
)