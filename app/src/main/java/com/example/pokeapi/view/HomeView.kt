package com.example.pokeapi.view

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.pokeapi.navigation.NavManager
import com.example.pokeapi.navigation.Routes
import com.example.pokeapi.view.components.BottomNav

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(){
    val navController = rememberNavController()
    val navigationRoutes = listOf(
        Routes.PokemonView,
        Routes.ItemView
    )

    Scaffold(
        bottomBar ={
            BottomNav(navController, navigationRoutes )
        }
    ){
        NavManager(navController)
    }
}