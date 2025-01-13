package com.example.pokeapi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes (
    val icon: ImageVector,
    val title: String,
    val route: String
){
    object PokemonView: Routes(Icons.Default.Phone, "Pokemon", "PokemonView")
    object ItemView: Routes(Icons.Default.MailOutline, "Item", "ItemView")
}