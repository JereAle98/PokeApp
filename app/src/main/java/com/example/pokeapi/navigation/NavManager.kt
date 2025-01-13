package com.example.pokeapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeapi.view.ItemsListView
import com.example.pokeapi.view.PokeListView


@Composable
fun NavManager(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Routes.PokemonView.route){
        composable(Routes.PokemonView.route) {
            PokeListView()
        }
        composable(Routes.ItemView.route) {
            ItemsListView()
        }

    }
}