package com.example.mypokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mypokedexapp.ui.screens.about.AboutScreen
import com.example.mypokedexapp.ui.screens.pokemon_detail.PokemonDetailScreen
import com.example.mypokedexapp.ui.screens.pokemon_list.PokemonListScreen
import com.example.mypokedexapp.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("pokemon_list") {
            PokemonListScreen(navController = navController)
        }
        composable(
            route = "pokemon_detail/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) {
            PokemonDetailScreen(navController = navController)
        }
        composable("about") {
            AboutScreen(navController = navController)
        }
    }
}