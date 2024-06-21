package com.example.interlink.ui.pages

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.interlink.ui.getViewModelFactory
import kotlinx.coroutines.delay

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun ActivityPage(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = viewModel(factory = getViewModelFactory()),
    ){
    val uiState by viewModel.uiState.collectAsState()

//   Column(modifier = modifier) {
//        Text("${uiState.newEvents}", color = Color.Black, style = MaterialTheme.typography.titleLarge)
//        uiState.events.forEach { event ->
//            Text(event.toString(), color = Color.Black, style = MaterialTheme.typography.bodyLarge)
//        }
//    }

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            // prob tenga q ser un R.string o algo asi
          Text(
              text = "  Activity",
              color = Color.Black,
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.Bold
          )
        }

        Row{
            LazyColumn {
                items(uiState.events) { event ->
                    Box(modifier = Modifier.padding(10.dp)){
                        EventCard(event = event)
                    }
                }
            }
        }
    }
}