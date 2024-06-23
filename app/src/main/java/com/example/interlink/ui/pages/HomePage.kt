package com.example.interlink.ui.pages

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.ui.components.EventCard
import com.example.interlink.ui.devices.StoredEventEntryViewModel
import com.example.interlink.ui.theme.md_theme_light_coffee

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    favDevViewModel : FavoritesEntryViewModel,
    storedEvents : StoredEventEntryViewModel
) {
    val recents by storedEvents.getRecentEvents().collectAsState(initial = emptyList())
    val favorites by favDevViewModel.getFavoritesId().collectAsState(initial = emptyList())

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = stringResource(id = R.string.home),
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Row {
            // Esto solo va a mostrar los 3 eventos mas recientes
            LazyColumn {
                items(recents) { event ->
                    Box(modifier = Modifier.padding(10.dp)) {
                        EventCard(event = event)
                    }
                }
            }
        }
    }
}
//        Column(
//            modifier = Modifier
//        ) {
//
//            favorites.forEach {
//                Text(text = it)
//            }
//        }


