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
import com.example.mypokedexapp.ui.screens.compare.CompareScreen
import com.example.mypokedexapp.ui.screens.favorites.FavoritesScreen
import com.example.mypokedexapp.ui.screens.onboarding.OnboardingScreen
import com.example.mypokedexapp.ui.screens.pokemon_detail.PokemonDetailScreen
import com.example.mypokedexapp.ui.screens.pokemon_list.PokemonListScreen
import com.example.mypokedexapp.ui.screens.settings.SettingsScreen
import com.example.mypokedexapp.ui.screens.splash.SplashScreen

// Navigasi level atas (Aplikasi)
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
        // PERBAIKAN: Pindahkan rute "about" ke sini agar bisa diakses dari mana saja
        // dalam rootNavController, termasuk dari SettingsScreen.
        composable("about") {
            AboutScreen(navController = navController)
        }
    }
}

// Navigasi level dalam (untuk Bottom Bar)
@Composable
fun AppNavigation(bottomBarNavController: NavHostController, rootNavController: NavHostController) {
    NavHost(bottomBarNavController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            PokemonListScreen(navController = rootNavController)
        }
        composable(BottomNavItem.Favorites.route) {
            // PERBAIKAN: Berikan rootNavController ke FavoritesScreen
            FavoritesScreen(navController = rootNavController)
        }
        composable(BottomNavItem.Compare.route) {
            CompareScreen() // Asumsi halaman ini belum butuh navigasi keluar
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen(navController = rootNavController)
        }
    }
}