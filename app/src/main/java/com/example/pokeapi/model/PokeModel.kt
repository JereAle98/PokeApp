package com.example.pokeapi.model

import com.example.pokeapi.data.response.PokeAbilitiesResponse
import com.example.pokeapi.data.response.PokeTypesResponse

data class PokeModel(
    val name: String,
    val url: String,

)

data class PokeDetailModel(
    val abilities: List<AbilitiesModel>,
    val types: List<TypesModel>
)

data class AbilitiesModel(
    val ability: String,
    val url: String
)

data class TypesModel(
    val slot: Int,
    val types: List<TypeModel>
)

data class TypeModel(
    val name: String,
    val url: String
)