package com.example.mypokedexapp.ui.screens.pokemon_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mypokedexapp.domain.model.Pokemon

// Helper function untuk konsistensi
private fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) this.first().uppercase() + this.substring(1) else this
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    // State untuk search query
    var searchQuery by remember { mutableStateOf("") }

    // PERBAIKAN: Scaffold dipindahkan ke sini agar TopAppBar konsisten
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari Pokémon...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Filter daftar pokemon berdasarkan pencarian
            val filteredList = state.pokemonList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            // Konten Utama
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading && filteredList.isEmpty()) {
                    CircularProgressIndicator()
                } else if (filteredList.isEmpty()) {
                    Text(
                        text = if (searchQuery.isNotEmpty()) "Tidak ada hasil untuk '$searchQuery'" else state.error ?: "Gagal memuat data.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), // 2 kolom
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredList) { pokemon ->
                            PokemonGridItem(
                                pokemon = pokemon,
                                onItemClick = {
                                    navController.navigate("pokemon_detail/${pokemon.name}")
                                },
                                // PERBAIKAN: Panggil fungsi onFavoriteClick dari ViewModel
                                onFavoriteClick = {
                                    viewModel.onFavoriteClick(pokemon)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonGridItem(
    pokemon: Pokemon,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(top = 8.dp)
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        // PERBAIKAN: Ganti ikon berdasarkan status favorit dari data
                        imageVector = if (pokemon.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (pokemon.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = pokemon.name.capitalizeFirstLetter(),
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}