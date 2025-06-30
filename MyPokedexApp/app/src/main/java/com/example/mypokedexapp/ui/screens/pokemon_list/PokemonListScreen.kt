package com.example.mypokedexapp.ui.screens.pokemon_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <-- IMPORT PENTING
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    // Sekarang kita hanya mengamati satu state saja
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") },
                actions = {
                    IconButton(onClick = { navController.navigate("about") }) {
                        Icon(Icons.Default.Info, contentDescription = "About Page")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Logika UI menjadi lebih bersih dan terstruktur
            if (state.isLoading && state.pokemonList.isEmpty()) {
                CircularProgressIndicator()
            } else if (state.pokemonList.isEmpty()) {
                Text(
                    text = state.error ?: "Gagal memuat data. Periksa koneksi internet Anda.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.pokemonList) { pokemon ->
                        PokemonListItem(pokemon = pokemon, onItemClick = {
                            navController.navigate("pokemon_detail/${pokemon.name}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                // PERBAIKAN: Menggunakan helper function yang sudah pasti berhasil
                text = pokemon.name.capitalizeFirstLetter(),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}