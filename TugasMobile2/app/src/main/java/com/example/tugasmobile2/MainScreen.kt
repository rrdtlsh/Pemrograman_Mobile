package com.example.tugasmobile2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val data by viewModel.data.collectAsState()
    val error by viewModel.error.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                data != null -> {
                    Text("Nama: ${data?.Nama}", style = MaterialTheme.typography.headlineSmall)
                    Text("NIM: ${data?.NIM}", style = MaterialTheme.typography.bodyLarge)
                    Text("Jurusan: ${data?.Jurusan}", style = MaterialTheme.typography.bodyLarge)
                }

                error != null -> {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }

                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
