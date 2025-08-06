package com.example.vknews.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Black900,
    secondary = Black900,
    surface = Black900,
    onPrimary = Color.White,
    onSecondary = Black500,
    onSurface = Black500,
    secondaryContainer = Black900,
    onSecondaryContainer = Color.White,

)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.White,
    surface = Color.White,
    onPrimary = Black900,
    onPrimaryContainer = Black900,
    onSecondary = Black500,
    onSurface = Black500,
    secondaryContainer = Color.White,
    onSecondaryContainer = Black900,
)

@Composable
fun VkNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}