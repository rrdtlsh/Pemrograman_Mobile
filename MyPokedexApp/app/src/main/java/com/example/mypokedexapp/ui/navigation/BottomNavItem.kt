package com.example.mypokedexapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
// PERBAIKAN: Import yang benar untuk ikon CompareArrows
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favorites")
    data object Compare : BottomNavItem("compare", Icons.AutoMirrored.Filled.CompareArrows, "Compare")
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}