package com.example.interlink.model

import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteAlarm
import com.example.interlink.remote.model.RemoteAlarmState

class Alarm (
    id: String?,
    name: String,
    val status: Status
) : Device(id, name, DeviceType.ALARM) {

    override fun asRemoteModel(): RemoteDevice<RemoteAlarmState> {
        val state = RemoteAlarmState()
        state.status = Status.asRemoteModel(status)

        val model = RemoteAlarm()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val ARM_STAY_ACTION = "armStay"
        const val ARM_AWAY_ACTION = "armAway"
        const val DISARM_ACTION = "disarm"
        const val CHANGE_SECURITY_CODE_ACTION = "changeSecurityCode"
    }
}