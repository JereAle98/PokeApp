package com.example.pokeapi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokeapi.R
import com.example.pokeapi.model.PokeModel
import com.example.pokeapi.ui.theme.principal
import com.example.pokeapi.view.components.SearchBar
import com.example.pokeapi.viewModel.PokeListViewModel

@Composable
fun PokeListView(pokeListViewModel: PokeListViewModel = hiltViewModel()) {
    val pokemons = pokeListViewModel.pokemons.collectAsLazyPagingItems()

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
                    .background(Color.Red), contentAlignment = Alignment.Center
            ) {
                Text(text = "Ha ocurrido un error")
            }
        }

        else -> {
            PokemonList(pokemons, pokeListViewModel)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(pokemons: LazyPagingItems<PokeModel>, pokeListViewModel: PokeListViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { },
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.pokeapi_256),
                        contentDescription = "title"
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(principal),
                modifier = Modifier.fillMaxWidth()

            )

        }
    ) { padding ->

        Column(modifier = Modifier.fillMaxSize().padding(padding)){
            PokemonSearchScreen(pokeListViewModel,pokemons)
            LazyColumn() {

                items(pokemons.itemCount) {
                    pokemons[it]?.let { pokeModel ->
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
                                                pokeModel.name.uppercase()
                                                    ?: "No Title",
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
                                viewModel = pokeListViewModel,
                                name,
                                closeClick = { showAlert = false }
                            )
                        }
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
    viewModel.fetchDetailPokemon(name)
    Box() {
        BasicAlertDialog(onDismissRequest = {}) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(5.dp, color = principal)
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center

            ) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Text(text = name.uppercase(), color = Color.Black)
                        IconButton(onClick = { closeClick() }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    pokemon?.let {
                        // Mostrar tipos
                        Text("TYPES: ${it.types.joinToString { type -> type.type.name.uppercase() }}",color = Color.Black)

                        // Mostrar habilidades
                        Text("ABILITIES: ${it.abilities.joinToString { ability -> ability.ability.name.uppercase() }}",color = Color.Black)

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun PokemonSearchScreen(viewModel: PokeListViewModel,pokemons: LazyPagingItems<PokeModel>) {
    val pokemon = viewModel.pokemon.value
    var query by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showAlert by remember { mutableStateOf(false) }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { viewModel.searchPokemon(query)
            showAlert = true}
        )


        if (pokemon != null) {
            if (showAlert){
                PokemonScreen(viewModel,pokemon.name, closeClick = {showAlert = false})
            }

        }
    }
}