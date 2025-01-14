package com.example.pokeapi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeapi.view.ItemListView
import com.example.pokeapi.view.PokeListView


@Composable
fun NavManager(navHostController: NavHostController, paddingValues: PaddingValues){
    NavHost(navController = navHostController, startDestination = Routes.PokemonView.route){
        composable(Routes.PokemonView.route) {
            PokeListView(paddingValues = paddingValues)
        }
        composable(Routes.ItemView.route) {
            ItemListView(paddingValues = paddingValues)
        }

    }
}