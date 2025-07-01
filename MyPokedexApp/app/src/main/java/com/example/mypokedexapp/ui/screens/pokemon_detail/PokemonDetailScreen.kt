package com.example.mypokedexapp.ui.screens.pokemon_detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
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
import com.example.mypokedexapp.ui.theme.PoppinsFamily

private fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val pokemon = state.pokemon

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = pokemon?.name?.capitalizeFirstLetter() ?: "Loading...",
                    fontFamily = PoppinsFamily
                )},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    pokemon?.let {
                        IconButton(onClick = { viewModel.toggleFavoriteStatus() }) {
                            Icon(
                                imageVector = if (it.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (it.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
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
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(
                    text = "Gagal memuat detail: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                pokemon != null -> PokemonDetailContent(pokemon = pokemon)
            }
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: PokemonDetail) {
    var animationPlayed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl.ifBlank { R.drawable.ic_pokeball_placeholder })
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_pokeball_placeholder),
                error = painterResource(id = R.drawable.ic_pokeball_placeholder),
                contentDescription = pokemon.name,
                modifier = Modifier.size(150.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            "#${pokemon.id} - ${pokemon.name.capitalizeFirstLetter()}",
            fontFamily = PoppinsFamily,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            pokemon.types.forEach { type ->
                Chip(label = type.name)
                Spacer(Modifier.width(8.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
        PokemonMeasurements(height = pokemon.height, weight = pokemon.weight)
        Spacer(Modifier.height(24.dp))
        PokemonStats(stats = pokemon.stats, animationPlayed = animationPlayed)
    }
}

@Composable
fun PokemonMeasurements(height: Double, weight: Double) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Height", style = MaterialTheme.typography.labelSmall)
            Text("${String.format("%.1f", height)} m", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Weight", style = MaterialTheme.typography.labelSmall)
            Text("${String.format("%.1f", weight)} kg", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PokemonStats(stats: List<Stat>, animationPlayed: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "Base Stats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )
        stats.forEach { stat ->
            val animatedProgress by animateFloatAsState(
                targetValue = if (animationPlayed) (stat.value / 255f) else 0f,
                animationSpec = tween(1000, 200),
                label = "statAnimation"
            )
            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(stat.name.capitalizeFirstLetter(), Modifier.weight(0.4f), fontSize = 14.sp)
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .weight(0.6f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = when {
                        animatedProgress > 0.6f -> Color(0xFF4CAF50)
                        animatedProgress > 0.4f -> Color(0xFFFFC107)
                        else -> Color(0xFFF44336)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(label: String) {
    AssistChip(
        onClick = { },
        label = { Text(label.uppercase(), fontWeight = FontWeight.Bold) }
    )
}