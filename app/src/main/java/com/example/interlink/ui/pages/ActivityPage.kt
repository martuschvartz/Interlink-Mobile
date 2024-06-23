package com.example.interlink.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.ui.components.DeleteNotifsActionButton
import com.example.interlink.ui.components.EventCard
import com.example.interlink.ui.devices.StoredEventEntryViewModel

// el modifier default es la misma clase Modifier, sino es el que le paso

@Composable
fun ActivityPage(
    modifier: Modifier = Modifier,
    storedEvents : StoredEventEntryViewModel,
    useLazyColumn : Boolean
){

    val stored by storedEvents.getStoredEvents().collectAsState(initial = emptyList())
    Log.d("DEBUG", "Encontramos: $stored")


    // Todo: agregar un boton para borrar las notifs que no sea el de activity
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.activity),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            DeleteNotifsActionButton {
                storedEvents.deleteAllEvents()
            }
        }

        Row(
            verticalAlignment = Alignment.Top
        ){
            if(useLazyColumn){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(stored) { event ->
                        Box(modifier = Modifier.padding(10.dp)) {
                            EventCard(event = event)
                        }
                    }
                }
            }
            else{
                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(stored) { event ->
                        Box(modifier = Modifier.padding(10.dp)) {
                            EventCard(event = event)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prev1(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = stringResource(id = R.string.activity),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
}