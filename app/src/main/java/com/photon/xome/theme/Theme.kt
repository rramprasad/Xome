package com.photon.xome.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = xomeorange,
    secondary = xomeorange,
    background = white,
    surface = white,
    onPrimary = xomeorange,
    onSecondary = xomeorange,
    onBackground = xomeorange,
    onSurface = xomeorange
)

private val DarkColorPalette = darkColors(
    primary = xomeorange,
    secondary = xomeorange,
    background = black,
    surface = black,
    onPrimary = xomeorange,
    onSecondary = xomeorange,
    onBackground = xomeorange,
    onSurface = xomeorange
)

@Composable
fun XomeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
