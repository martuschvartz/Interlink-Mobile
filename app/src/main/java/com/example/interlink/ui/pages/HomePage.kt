package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.interlink.ui.devices.FavoritesEntryViewModel

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    favDevViewModel : FavoritesEntryViewModel
){

    val favorites by favDevViewModel.getFavoritesId().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
    ) {

        favorites.forEach { 
            Text(text = it)
        }
    }
}


