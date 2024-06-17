package com.example.interlink

import androidx.compose.ui.tooling.preview.Preview

// el default es ingles, osea si no le pongo cual es el locale toma ingles
@Preview(
    name = "english",
    group = "locales"
)

@Preview(
    name = "español",
    group = "locales",
    locale = "es"
)

annotation class PreviewLocales()
