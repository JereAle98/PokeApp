package com.example.pokeapi.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pokeapi.R
import com.example.pokeapi.ui.theme.principal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
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