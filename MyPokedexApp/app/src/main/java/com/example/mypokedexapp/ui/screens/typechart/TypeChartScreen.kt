package com.example.mypokedexapp.ui.screens.typechart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypokedexapp.domain.model.PokemonType
import com.example.mypokedexapp.domain.model.TypeChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeChartScreen() {
    val types = PokemonType.values().toList()
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tabel Kelemahan Tipe") }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .horizontalScroll(horizontalScrollState)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size(60.dp))
                    types.forEach { attackingType ->
                        TypeHeaderCell(type = attackingType)
                    }
                }
                types.forEach { defendingType ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TypeHeaderCell(type = defendingType)
                        types.forEach { attackingType ->
                            val multiplier = TypeChart.effectiveness[attackingType]?.get(defendingType) ?: 1f
                            EffectivenessCell(multiplier = multiplier)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TypeHeaderCell(type: PokemonType) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .background(type.color)
            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.name.take(3),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EffectivenessCell(multiplier: Float) {
    val (backgroundColor, textColor) = when (multiplier) {
        2f -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        0.5f -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        0f -> Color.DarkGray to Color.White
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(2.dp)
            .background(backgroundColor, shape = MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        val text = when (multiplier) {
            2f -> "2x"
            0.5f -> "½"
            0f -> "0"
            else -> ""
        }
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}