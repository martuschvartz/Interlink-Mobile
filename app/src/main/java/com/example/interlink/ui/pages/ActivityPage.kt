package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.getViewModelFactory
import kotlinx.coroutines.delay

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun ActivityPage(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    ){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.callGetEvent()
            delay(5000) // Adjust the delay time as needed
        }
    }

    Column(modifier = modifier) {
        Text("${uiState.newEvents}", color = Color.Black, style = MaterialTheme.typography.titleLarge)
        uiState.events.forEach { event ->
            Text(event.toString(), color = Color.Black, style = MaterialTheme.typography.bodyLarge)
        }
    }
}