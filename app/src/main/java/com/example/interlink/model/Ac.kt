package com.example.interlink.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.interlink.remote.model.RemoteAc
import com.example.interlink.remote.model.RemoteAcState
import com.example.interlink.remote.model.RemoteDevice

/*
"status": "off",
        "temperature": 24,
        "mode": "cool",
        "verticalSwing": "auto",
        "horizontalSwing": "auto",
        "fanSpeed": "auto"

 */
class Ac (
    id: String?,
    name: String,
    val status: Status,
    val temperature: Int,
    val mode: String,
    val verticalSwing: String,
    val horizontalSwing: String,
    val fanSpeed: String,
) : Device(id, name, DeviceType.AC) {

    override fun asRemoteModel(): RemoteDevice<RemoteAcState> {
        val state = RemoteAcState()
        state.status = Status.asRemoteModel(status)
        state.temperature = temperature
        state.mode = mode
        state.verticalSwing = verticalSwing
        state.horizontalSwing = horizontalSwing
        state.fanSpeed = fanSpeed

        val model = RemoteAc()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    fun getImageVector(): ImageVector? {
        return when(mode){
            "cool" -> Icons.Default.AcUnit
            "heat" -> Icons.Default.WbSunny
            "fan" -> Icons.Default.Air
            else -> null
        }
    }

    companion object {
        const val TURN_ON_ACTION = "turnOn"
        const val TURN_OFF_ACTION = "turnOff"
        const val SET_TEMPERATURE_ACTION = "setTemperature"
        const val SET_MODE_ACTION = "setMode"
        const val SET_VERTICAL_SWING_ACTION = "setVerticalSwing"
        const val SET_HORIZONTAL_SWING_ACTION = "setHorizontalSwing"
        const val SET_FAN_SPEED_ACTION = "setFanSpeed"

    }
}