package com.example.pokeapi.model

data class PokeWrapperModel (
    val count:Int,
    val prev:String?,
    val next:String?,
    val results:List<PokeModel>
)
