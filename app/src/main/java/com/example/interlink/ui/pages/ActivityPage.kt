package com.example.interlink.ui.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.remote.db.DatabaseProvider
import com.example.interlink.ui.alerts.AlertViewModel
import com.example.interlink.ui.alerts.AlertViewModelFactory
import com.example.interlink.model.Alert
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityPage(context: Context) {
    val database = remember { DatabaseProvider.getDatabase(context) }
    val viewModelFactory = remember { AlertViewModelFactory(database.alertDao()) }
    val viewModel: AlertViewModel = viewModel(factory = viewModelFactory)

    val alerts by viewModel.getAlertsByStatus("new").observeAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                // Insertar una nueva alerta
                val title = "New Alert"
                val message = "This is a test alert"
                val timestamp = System.currentTimeMillis()
                val status = "new"
                viewModel.insertAlert( title, message, timestamp, status)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Alert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(alerts) { alert ->
                AlertItem(alert)
            }
        }
    }
}


@Composable
fun AlertItem(alert: Alert) {
    val dateFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
    val formattedTimestamp = dateFormat.format(Date(alert.timestamp))
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = alert.title, style = MaterialTheme.typography.titleLarge)
        Text(text = alert.message, style = MaterialTheme.typography.bodyMedium)
        Text(text = formattedTimestamp, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun ActivityPagePreview(){
    ActivityPage(LocalContext.current)
}

