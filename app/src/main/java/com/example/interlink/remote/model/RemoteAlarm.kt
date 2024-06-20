package com.example.interlink.remote.model

import com.example.interlink.model.Alarm

class RemoteAlarm : RemoteDevice<RemoteAlarmState>() {
    override fun asModel(): Alarm {
        return Alarm(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status)
        )
    }
}