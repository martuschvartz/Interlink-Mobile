package com.example.interlink.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)*/

private val LightColorScheme = lightColorScheme(
    background = md_theme_light_background,
    onBackground = Color(0x00000000),
    onSurface = Color(0x00000000),
    primaryContainer = md_theme_light_coffee,
    onPrimaryContainer = md_theme_light_background,
    primary = md_theme_light_intergreen,
    onPrimary = Color(0x00000000),
    secondary = md_theme_light_intergrey,
    onSecondary = Color(0x00000000),
    tertiary = md_theme_light_interblue,
    onTertiary = Color(0x00000000),
    error = md_theme_light_interred,
    onError = Color(0x00000000),

)

private val DarkColorScheme = lightColorScheme(
    background = md_theme_dark_background,
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0x00000000),
    primaryContainer = md_theme_light_coffee,
    onPrimaryContainer = md_theme_light_background,
    primary = md_theme_light_intergreen,
    onPrimary = Color(0x00000000),
    secondary = md_theme_light_intergrey,
    onSecondary = Color(0x00000000),
    tertiary = md_theme_light_interblue,
    onTertiary = Color(0x00000000),
    error = md_theme_light_interred,
    onError = Color(0x00000000),

    )


@Composable
fun InterlinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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