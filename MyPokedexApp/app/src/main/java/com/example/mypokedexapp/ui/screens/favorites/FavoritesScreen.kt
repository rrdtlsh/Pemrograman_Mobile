package com.example.mypokedexapp.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mypokedexapp.domain.model.Pokemon
import com.example.mypokedexapp.ui.screens.pokemon_list.PokemonGridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesList by viewModel.favoritesState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pokémon Favorit") }) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari di Favorit...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            val filteredList = favoritesList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotEmpty()) "Tidak ada hasil" else "Anda belum punya Pokémon favorit.",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredList) { pokemonDetail ->
                        PokemonGridItem(
                            pokemon = Pokemon(
                                name = pokemonDetail.name,
                                imageUrl = pokemonDetail.imageUrl,
                                isFavorite = pokemonDetail.isFavorite
                            ),
                            onItemClick = {
                                navController.navigate("pokemon_detail/${pokemonDetail.name}")
                            },
                            onFavoriteClick = {
                                viewModel.toggleFavorite(pokemonDetail.name, false)
                            }
                        )
                    }
                }
            }
        }
    }
}