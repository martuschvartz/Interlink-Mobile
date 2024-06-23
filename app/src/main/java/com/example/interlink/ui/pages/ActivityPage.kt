package com.example.interlink.ui.pages

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.R
import com.example.interlink.database.StoredEventData
import com.example.interlink.model.Ac
import com.example.interlink.model.Alarm
import com.example.interlink.model.Blinds
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp
import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.components.EventCard
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.EventsViewModel
import com.example.interlink.ui.devices.FavoritesEntryViewModel
import com.example.interlink.ui.devices.StoredEventEntryViewModel
import com.example.interlink.ui.devices.StoredEventEntryViewModelFactory
import com.example.interlink.ui.getViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun ActivityPage(
    modifier: Modifier = Modifier,
    storedEvents : StoredEventEntryViewModel,
    viewModel: EventsViewModel = viewModel(factory = getViewModelFactory())
){

    val stored by storedEvents.getStoredEvents().collectAsState(initial = emptyList())
    Log.d("DEBUG", "Encontramos: $stored")
    val coroutineScope = rememberCoroutineScope()


    // Todo: agregar un boton para borrar las notifs que no sea el de activity
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .clickable {
                    storedEvents.deleteAllEvents()
                },

            horizontalArrangement = Arrangement.Start,
        ){
          Text(
              text = stringResource(id = R.string.activity),
              color = MaterialTheme.colorScheme.onSurface,
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.Bold,
          )
        }

        Row{
            LazyColumn {
                items(stored) { event ->
                    Box(modifier = Modifier.padding(10.dp)){
                        EventCard(event = event)
                    }
                }
            }
        }
    }
}