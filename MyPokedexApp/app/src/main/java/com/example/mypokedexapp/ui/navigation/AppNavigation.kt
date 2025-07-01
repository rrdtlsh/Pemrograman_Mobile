package com.example.mypokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mypokedexapp.ui.screens.MainScreen
import com.example.mypokedexapp.ui.screens.about.AboutScreen
import com.example.mypokedexapp.ui.screens.favorites.FavoritesScreen
import com.example.mypokedexapp.ui.screens.onboarding.OnboardingScreen
import com.example.mypokedexapp.ui.screens.pokemon_detail.PokemonDetailScreen
import com.example.mypokedexapp.ui.screens.pokemon_list.PokemonListScreen
import com.example.mypokedexapp.ui.screens.settings.SettingsScreen
import com.example.mypokedexapp.ui.screens.splash.SplashScreen
import com.example.mypokedexapp.ui.screens.typechart.TypeChartScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        composable("main") {
            MainScreen(rootNavController = navController)
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

@Composable
fun AppNavigation(bottomBarNavController: NavHostController, rootNavController: NavHostController) {
    NavHost(bottomBarNavController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            PokemonListScreen(navController = rootNavController)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen(navController = rootNavController)
        }
        composable(BottomNavItem.TypeChart.route) {
            TypeChartScreen()
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen(navController = rootNavController)
        }
    }
}