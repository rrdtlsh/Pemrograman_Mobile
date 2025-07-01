package com.example.mypokedexapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Red700,
    secondary = Blue500,
    tertiary = Yellow500,
    background = LightGray,
    surface = LightGray,
    onPrimary = White,
    onSecondary = White,
    onTertiary = Black,
    onBackground = DarkGray,
    onSurface = DarkGray,
)

private val DarkColorScheme = darkColorScheme(
    primary = Red300,
    secondary = Blue200,
    tertiary = Yellow200,
    background = DarkGray,
    surface = DarkGray,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = TextOnDark,
    onSurface = TextOnDark,
)

@Composable
fun MyPokedexAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}