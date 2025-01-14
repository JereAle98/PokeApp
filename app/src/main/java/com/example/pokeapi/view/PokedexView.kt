package com.example.pokeapi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.pokeapi.data.response.ResponseWrapper
import com.example.pokeapi.model.PokeModel
import com.example.pokeapi.ui.theme.principal
import com.example.pokeapi.viewModel.PokeListViewModel
import kotlinx.coroutines.launch

@Composable
fun PokeListView(
    pokeListViewModel: PokeListViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val pokemons = pokeListViewModel.pokemons.collectAsLazyPagingItems()
    val searchQuery = remember { mutableStateOf("") }

    when {

        pokemons.loadState.refresh is LoadState.Loading && pokemons.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp), color = Color.Gray
                )
            }
        }

        pokemons.loadState.refresh is LoadState.NotLoading && pokemons.itemCount == 0 -> {
            Text(text = "Todavía no hay pokémons")
        }

        pokemons.loadState.hasError -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(principal), contentAlignment = Alignment.Center
            ) {
                Text(text = "Ha ocurrido un error", color = Color.White)
            }
        }

        else -> {
            PokemonList(pokemons, pokeListViewModel, searchQuery, paddingValues)

            if (pokemons.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp), color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemons: LazyPagingItems<PokeModel>,
    pokeListViewModel: PokeListViewModel,
    searchQuery: MutableState<String>,
    paddingValues: PaddingValues
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        PokemonSearchScreen(searchQuery)
        LazyColumn() {
            items(pokemons.itemCount) {
                if (searchQuery.value.isNotEmpty() && searchQuery.value.length >= 3 ) {

                        if (pokemons[it]?.name?.contains(
                                searchQuery.value,
                                ignoreCase = true
                            ) == true
                        ) {
                            pokemons[it]?.let { pokeModel ->
                                RenderPokemon(pokeModel, pokeListViewModel)
                            }
                        }


                } else {
                    pokemons[it]?.let { pokeModel ->
                        RenderPokemon(pokeModel, pokeListViewModel)
                    }
                }
            }

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(viewModel: PokeListViewModel, name: String, closeClick: () -> Unit) {
    val pokemon = viewModel.pokemonDetail.value
    val pokemonImage = viewModel.pokemonImage.value
    viewModel.fetchDetailPokemon(name)
    viewModel.fetchImagePokemon(name)
    Box() {
        BasicAlertDialog(onDismissRequest = {}) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(5.dp, color = principal, shape = RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp),

                ) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text(
                            text = name.uppercase(),
                            color = Color.Black,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                        IconButton(onClick = { closeClick() }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    pokemon?.let {
                        Text(
                            "TYPES: ${it.types.joinToString { type -> type.type.name.uppercase() }}",
                            color = Color.Black,
                            textAlign = TextAlign.Justify
                        )

                        Text(
                            "ABILITIES: ${it.abilities.joinToString { ability -> ability.ability.name.uppercase() }}",
                            color = Color.Black,
                            textAlign = TextAlign.Justify
                        )

                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        pokemonImage?.let {

                            Image(
                                painter = rememberAsyncImagePainter(it.sprites.imageFront),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonSearchScreen(searchQuery: MutableState<String>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Enter Pokémon") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RenderPokemon(pokeModel: PokeModel, viewModel: PokeListViewModel) {
    var showAlert by remember { mutableStateOf(false) }
    ListItem(
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAlert = true }) {
                Row {
                    Column {
                        Text(
                            text = pokeModel.name.uppercase() ?: "No Title",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                )
            }
        }
    )
    val name = pokeModel.name
    if (showAlert) {
        PokemonScreen(
            viewModel = viewModel,
            name,
            closeClick = { showAlert = false }
        )
    }
}