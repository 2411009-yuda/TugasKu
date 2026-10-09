package com.example.tugasku.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = AksenTerang,
    onPrimary = Color.White,
    primaryContainer = AksenWadahTerang,
    onPrimaryContainer = TeksAksenWadahTerang,
    secondary = AksenTerang,
    onSecondary = Color.White,
    secondaryContainer = AksenWadahTerang,
    onSecondaryContainer = TeksAksenWadahTerang,
    background = LatarTerang,
    onBackground = TeksTerang,
    surface = PermukaanTerang,
    onSurface = TeksTerang,
    surfaceVariant = GarisHalusTerang,
    onSurfaceVariant = TeksRedupTerang,
    outline = GarisTerang,
    outlineVariant = GarisHalusTerang,
    error = GalatTerang,
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = AksenGelap,
    onPrimary = Color(0xFF0F2E2C),
    primaryContainer = AksenWadahGelap,
    onPrimaryContainer = TeksAksenWadahGelap,
    secondary = AksenGelap,
    onSecondary = Color(0xFF0F2E2C),
    secondaryContainer = AksenWadahGelap,
    onSecondaryContainer = TeksAksenWadahGelap,
    background = LatarGelap,
    onBackground = TeksGelap,
    surface = PermukaanGelap,
    onSurface = TeksGelap,
    surfaceVariant = GarisHalusGelap,
    onSurfaceVariant = TeksRedupGelap,
    outline = GarisGelap,
    outlineVariant = GarisHalusGelap,
    error = GalatGelap,
    onError = Color(0xFF3A110A)
)

private val BentukMinimalis = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)

// Warna dinamis sistem sengaja dimatikan agar palet minimalis selalu konsisten.
@Composable
fun TugasKuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = BentukMinimalis,
        content = content
    )
}