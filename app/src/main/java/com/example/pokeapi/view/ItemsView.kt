package com.example.pokeapi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.example.pokeapi.R
import com.example.pokeapi.model.PokeModel
import com.example.pokeapi.navigation.Routes
import com.example.pokeapi.ui.theme.principal
import com.example.pokeapi.viewModel.PokeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListView() {

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
            Text(text = "ItemView")

        }
    }
}