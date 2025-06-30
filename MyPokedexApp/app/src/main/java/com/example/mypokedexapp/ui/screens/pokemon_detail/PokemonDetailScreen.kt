package com.example.mypokedexapp.ui.screens.pokemon_detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mypokedexapp.R
import com.example.mypokedexapp.domain.model.PokemonDetail
import com.example.mypokedexapp.domain.model.Stat

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
    val pokemon = state.pokemon

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = pokemon?.name?.capitalizeFirstLetter() ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (pokemon != null) {
                        IconButton(onClick = { viewModel.toggleFavoriteStatus() }) {
                            Icon(
                                imageVector = if (pokemon.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (pokemon.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
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
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                pokemon != null -> {
                    PokemonDetailContent(pokemon = pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: PokemonDetail) {
    // State untuk memicu animasi
    var animationPlayed by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.imageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_pokeball_placeholder) // Ganti dengan drawable Anda
                .error(R.drawable.ic_pokeball_placeholder)
                .build(),
            contentDescription = pokemon.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "#${pokemon.id} - ${pokemon.name.capitalizeFirstLetter()}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            pokemon.types.forEach { type ->
                Chip(label = type.name)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        PokemonMeasurements(height = pokemon.height, weight = pokemon.weight)
        Spacer(modifier = Modifier.height(24.dp))
        // Panggil PokemonStats di sini dengan trigger animasi
        PokemonStats(stats = pokemon.stats, animationPlayed = animationPlayed)
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
fun PokemonStats(stats: List<Stat>, animationPlayed: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Base Stats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )
        stats.forEach { stat ->
            // Animasikan progress bar agar terisi perlahan
            val animatedProgress by animateFloatAsState(
                targetValue = if (animationPlayed) (stat.value / 255f) else 0f,
                animationSpec = tween(durationMillis = 1000, delayMillis = 200),
                label = "statAnimation"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stat.name.capitalizeFirstLetter(),
                    modifier = Modifier.weight(0.3f)
                )
                // Gunakan progress yang sudah dianimasikan
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier.weight(0.7f).height(10.dp).clip(CircleShape),
                    color = if (animatedProgress > 0.5f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
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