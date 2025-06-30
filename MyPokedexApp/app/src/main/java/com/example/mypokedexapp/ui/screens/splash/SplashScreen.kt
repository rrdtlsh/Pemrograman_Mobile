package com.example.mypokedexapp.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mypokedexapp.R // Pastikan import R sudah benar

@Composable
fun SplashScreen(navController: NavController) {
    // 1. Muat komposisi animasi dari folder res/raw
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.pokeball_animation) // Ganti nama file jika perlu
    )

    // 2. Dapatkan progress animasi saat ini (dari 0.0 sampai 1.0)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1 // Mainkan animasi 1 kali saja
    )

    // 3. Gunakan LaunchedEffect untuk bernavigasi setelah animasi selesai
    LaunchedEffect(progress) {
        // Jika progress sudah mencapai 1.0 (artinya animasi selesai)
        if (progress == 1.0f) {
            navController.popBackStack() // Hapus splash screen dari back stack
            navController.navigate("pokemon_list")
        }
    }

    // Tampilkan animasi di tengah layar
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Sesuaikan warna latar belakang
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxSize(0.6f) // Ukuran animasi 60% dari layar
        )
    }
}