package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CommandStationColorScheme = darkColorScheme(
    primary = PrimaryCyan,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryCyanContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = SecondaryCritical,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryCriticalContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TertiaryAmber,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryAmberContainer,
    background = DeepObsidian,
    onBackground = OnSurface,
    surface = DeepObsidian,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = OutlineGrey,
    outlineVariant = OutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // ICU Command Center is designed in professional high-contrast dark telemetry theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CommandStationColorScheme,
        typography = Typography,
        content = content
    )
}
