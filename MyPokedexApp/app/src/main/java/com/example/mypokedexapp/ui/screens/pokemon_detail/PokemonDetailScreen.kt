package com.example.mypokedexapp.ui.screens.pokemon_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.model.Stat

// Helper function untuk kapitalisasi agar kode utama lebih bersih
private fun String.capitalizeFirstLetter(): String {
    return if (this.isNotEmpty()) this.first().uppercase() + this.substring(1) else this
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                // PERBAIKAN: Logika ditarik keluar dan dibuat sangat eksplisit.
                title = {
                    val pokemonName = state.pokemon?.name
                    val displayText = if (pokemonName.isNullOrEmpty()) {
                        "Loading..."
                    } else {
                        pokemonName.capitalizeFirstLetter()
                    }
                    Text(text = displayText)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                state.pokemon != null -> {
                    PokemonDetailContent(pokemon = state.pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: PokemonDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            // PERBAIKAN: Menggunakan helper function
            text = "#${pokemon.id} - ${pokemon.name.capitalizeFirstLetter()}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            pokemon.types.forEach { type ->
                Chip(label = type.name)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        PokemonMeasurements(height = pokemon.height, weight = pokemon.weight)
        Spacer(modifier = Modifier.height(24.dp))
        PokemonStats(stats = pokemon.stats)
    }
}

@Composable
fun PokemonMeasurements(height: Double, weight: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Height", style = MaterialTheme.typography.titleMedium)
            Text("${String.format("%.1f", height)} m", style = MaterialTheme.typography.bodyLarge)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Weight", style = MaterialTheme.typography.titleMedium)
            Text("${String.format("%.1f", weight)} kg", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun PokemonStats(stats: List<Stat>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Base Stats",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        stats.forEach { stat ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    // PERBAIKAN: Menggunakan helper function
                    text = stat.name.capitalizeFirstLetter(),
                    modifier = Modifier.weight(0.3f)
                )
                LinearProgressIndicator(
                    progress = stat.value / 255f,
                    modifier = Modifier.weight(0.7f).height(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(label: String) {
    AssistChip(
        onClick = {},
        label = { Text(text = label.uppercase()) }
    )
}