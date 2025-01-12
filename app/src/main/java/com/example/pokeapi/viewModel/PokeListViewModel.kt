package com.example.pokeapi.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pokeapi.data.PokeRepository
import com.example.pokeapi.data.response.PokeResponse
import com.example.pokeapi.model.DetailsModel
import com.example.pokeapi.model.PokeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor
    (private val pokeRepository: PokeRepository): ViewModel(){

    private val _pokemon = mutableStateOf<PokeResponse?>(null)
    val pokemon: State<PokeResponse?> = _pokemon


    private val _pokemonDetail = mutableStateOf<PokeResponse?>(null)
    val pokemonDetail: State<PokeResponse?> get() = _pokemonDetail

    val pokemons: Flow<PagingData<PokeModel>> = pokeRepository.getAllPokemons()

    fun fetchDetailPokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = pokeRepository.getPokemonByName(name)
                _pokemonDetail.value = result
            } catch (e: Exception) {
                _pokemonDetail.value = null
            }
        }
    }

    fun searchPokemon(query: String) {
        viewModelScope.launch {
            try {
                val result = pokeRepository.getPokemonByName(query.lowercase())
                _pokemon.value = result
            } catch (e: Exception) {
                _pokemon.value = null // Manejar errores
            }
        }
    }
}