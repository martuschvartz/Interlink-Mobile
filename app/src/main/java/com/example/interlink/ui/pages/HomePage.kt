package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.theme.md_theme_light_coffee

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    favDevViewModel : FavoritesEntryViewModel
) {

    val favorites by favDevViewModel.getFavoritesId().collectAsState(initial = emptyList())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn() {
            item() {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = md_theme_light_coffee
                    ),
                    modifier = Modifier
                        .size(300.dp)
                        .padding(3.dp),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
        ) {

            favorites.forEach {
                Text(text = it)
            }
        }
    }
}


