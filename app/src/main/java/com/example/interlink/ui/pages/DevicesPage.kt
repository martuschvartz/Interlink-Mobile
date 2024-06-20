package com.example.interlink.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interlink.model.DeviceType
import com.example.interlink.model.Door
import com.example.interlink.model.Lamp
import com.example.interlink.ui.components.DeviceCard
import com.example.interlink.ui.devices.DevicesViewModel
import com.example.interlink.ui.devices.DoorViewModel
import com.example.interlink.ui.devices.LampViewModel
import com.example.interlink.ui.getViewModelFactory

// el modifier default es la misma clase Modifier, sino es el que le paso

// Algo importante: Todo lo q esta adentro de model, remote y repository no es *necesario* q lo leas
// Si lo queres leer by all means go ahead asi entendes mas mejor q esta pasando con el api pero te deje las cosas para q
// esten lo mas simples posibles para q puedas meterle al ui y te despreocupes por el api
// Obvio q si algo esta mal avisame

@Composable
fun DevicesPage(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    lampViewModel: LampViewModel = viewModel(factory = getViewModelFactory()),
    doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
){

    // Estas variables iniciales son las que podemos usar para ver los estados de los devices, la idea es la siguiente:
        // El uiState generico (el primero) es el que tiene la lista de devices y se actualiza sola, es genial
        // Cada tipo de dispositivo tiene a su vez su propio uiState para poder controlar sus funciones, andate a interlink/ui/devices/DoorUiState para ver el ejemplo
    val uiState by viewModel.uiState.collectAsState()
    val uiLampState by lampViewModel.uiState.collectAsState()
    val uiDoorState by doorViewModel.uiState.collectAsState()
    var selectedDeviceId : String? = null

    // ===========================================================================================
    // A partir de aca irian los devices, atm solo tengo hecho lo de door y lamp pero los demas
    // salen facil, lo dificil era la infraestructura general q ya esta 10 puntos, estos botones
    // si prendes la api y agregas una(s) puertas/lamparas van a funcionar y ya hacen los llamados
    // y demas con la api, si esta apagada va a tirar error en la consola (allegedly)
    // ===========================================================================================

    // Columna generica
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            // Trate de dejarte esto lo mas generico posible para q si cambias DeviceItem por DeviceCard (o como la quieras llamar) no sea dificil hacer el refractoring
            // Si no me dan mal los calculos solo tendrias que cambiar esto de aca para poner tus cards
            items(uiState.devices) { device ->
                Box(modifier = Modifier.padding(10.dp)){
                    val deviceViewModel = when(device.type){
                        DeviceType.LAMP -> lampViewModel
                        DeviceType.SPEAKER -> null
                        DeviceType.BLINDS -> null
                        DeviceType.ALARM -> null
                        DeviceType.DOOR -> doorViewModel
                        DeviceType.AC -> null
                    }
                    DeviceCard(device = device, viewModel = deviceViewModel ) { device ->
                        // Algo copado q encontre es este when() que funciona igual a un switch, lo re podes usar dentro de deviceCard en si :D!
                        when(device.type){
                            DeviceType.LAMP -> lampViewModel.setCurrentDevice(device as Lamp)
                            DeviceType.DOOR -> doorViewModel.setCurrentDevice(device as Door)
                            else -> {}
                        }
                    }
                }
            }
        }

    }



}

/*
@Composable
fun DeviceList(devices: List<Device>, onDeviceClick: (Device) -> Unit) {
   LazyColumn {
       // Trate de dejarte esto lo mas generico posible para q si cambias DeviceItem por DeviceCard (o como la quieras llamar) no sea dificil hacer el refractoring
       // Si no me dan mal los calculos solo tendrias que cambiar esto de aca para poner tus cards
        items(devices) { device ->
            Box(modifier = Modifier.padding(10.dp)){
                DeviceCard(device = device, viewModel = , onClick = { onDeviceClick(device) })
            }
        }
    }

}
*/



/*
@Composable
fun <T : Device> DeviceItem(device: T, onClick: () -> Unit) {
    // Nada importante aca, solo q la carta es clickeable y q tiene un outline, no mas q eso
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = device.name, color=Color.Black, style = MaterialTheme.typography.titleLarge)
        }
    }
}*/

