package com.example.pokeapi.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pokeapi.data.PokeRepository
import com.example.pokeapi.data.response.FormResponse
import com.example.pokeapi.data.response.PokeDetailResponse
import com.example.pokeapi.model.PokeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(private val pokeRepository: PokeRepository): ViewModel() {

    private val _pokemonDetail = mutableStateOf<PokeDetailResponse?>(null)
    val pokemonDetail: State<PokeDetailResponse?> get() = _pokemonDetail


    private val _pokemonImage = mutableStateOf<FormResponse?>(null)
    val pokemonImage: State<FormResponse?> get() = _pokemonImage

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

    fun fetchImagePokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = pokeRepository.getPokemonImage(name)
                _pokemonImage.value = result
            } catch (e: Exception) {
                _pokemonImage.value = null
            }
        }
    }

    //    fun searchPokemon(query: String) {
//        viewModelScope.launch {
//            try {
//                val searchQuery = query.lowercase()
//                val result = pokeRepository.getAllPokemons()
//                _pokemonDetail.value =
//            } catch (e: Exception) {
//                _pokemonDetail.value = null // Manejar errores
//            }
//        }
//    }


}