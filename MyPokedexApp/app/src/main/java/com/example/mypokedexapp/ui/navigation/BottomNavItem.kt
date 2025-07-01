package com.example.mypokedexapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Calculate

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favorites")
    data object TypeChart : BottomNavItem("type_chart", Icons.Default.Calculate, "Tipe")
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}