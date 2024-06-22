package com.example.interlink.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.interlink.BuildConfig
import com.example.interlink.MyIntent
import com.example.interlink.remote.model.EventData
import com.example.interlink.remote.model.RemoteEvent
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.interlink.model.Device
import com.example.interlink.model.DeviceType
import com.example.interlink.model.EventDevice
import com.example.interlink.remote.api.DateTypeAdapter
import com.example.interlink.remote.api.DeviceTypeAdapter
import com.example.interlink.remote.model.RemoteDevice
import com.example.interlink.remote.model.RemoteDeviceType
import com.example.interlink.remote.model.RemoteEventDevice
import com.example.interlink.remote.model.RemoteResult
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date
import org.json.JSONObject

class ServerEventReceiver : BroadcastReceiver() {

    private val gson = Gson()
    private val dson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, DateTypeAdapter())
        .registerTypeAdapter(com.example.interlink.remote.model.RemoteDevice::class.java, DeviceTypeAdapter())
        .create()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received.")

        GlobalScope.launch(Dispatchers.IO) {
            val events = fetchEvents()
//            var remoteEvents : List<RemoteEvent> = emptyList()

            events?.forEach {

                fetchDevice(it.deviceId)

                Log.d(TAG, "Broadcasting send notification intent (${it.deviceId})")
                val intent2 = Intent().apply {
                    action = MyIntent.SHOW_NOTIFICATION
                    `package` = MyIntent.PACKAGE
                    // Esto tendria que ser el evento
                    putExtra(MyIntent.DEVICE_ID, it.deviceId)
                }
                context?.sendOrderedBroadcast(intent2, null)
            }
        }
    }

    private fun fetchEvents(): List<EventData>? {

        Log.d(TAG, "Fetching events...")
        val url = "${BuildConfig.API_BASE_URL}devices/events"
        val connection = (URL(url).openConnection() as HttpURLConnection).also {
            it.requestMethod = "GET"
            it.setRequestProperty("Accept", "application/json")
            it.setRequestProperty("Content-Type", "text/event-stream")
            it.doInput = true
        }

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<EventData>()
            while (stream.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }

                    line!!.isEmpty() -> {
                        Log.d(TAG, response.toString())
                        val event = gson.fromJson<EventData>(
                            response.toString(),
                            object : TypeToken<EventData?>() {}.type
                        )
                        Log.d(TAG, "El evento es ${event}")

                        eventList.add(event)
                        response.setLength(0)
                    }
                }
            }
            stream.close()
            connection.disconnect()
            Log.d(TAG, "New events found (${eventList.size})")
            eventList
        } else {
            Log.d(TAG, "No new events found")
            null
        }
    }

    private fun fetchDevice(id: String): Device? {

        Log.d(TAG, "Fetching device with id ${id}...")
        val url = "${BuildConfig.API_BASE_URL}devices/${id}"
        val connection = (URL(url).openConnection() as HttpURLConnection).also {
            it.requestMethod = "GET"
            it.setRequestProperty("Accept", "application/json")
            it.setRequestProperty("Content-Type", "text/event-stream")
            it.doInput = true
        }

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = BufferedReader(InputStreamReader(connection.inputStream)).use {
                it.readText()
            }


            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONObject("result")

            val id = result.getString("id")
            val name = result.getString("name")
            val typeId = result.getJSONObject("type").getString("id")
            val type = when(typeId){
                RemoteDeviceType.AC_DEVICE_TYPE_ID -> DeviceType.AC
                RemoteDeviceType.ALARM_DEVICE_TYPE_ID -> DeviceType.ALARM
                RemoteDeviceType.BLINDS_DEVICE_TYPE_ID-> DeviceType.BLINDS
                RemoteDeviceType.SPEAKER_DEVICE_TYPE_ID -> DeviceType.SPEAKER
                RemoteDeviceType.DOOR_DEVICE_TYPE_ID -> DeviceType.DOOR
                RemoteDeviceType.LAMP_DEVICE_TYPE_ID -> DeviceType.LAMP
                else -> DeviceType.LAMP
            }
            var newDev = EventDevice(id, name, type)
            Log.d(TAG, "Device es: ${newDev.type}")
            null

        } else {
            Log.d(TAG, "No device found")
            null
        }
    }

    private fun parseEvents(events : List<EventData>) : List<RemoteEvent>{
        var toRet : List<RemoteEvent> = emptyList()
        events.forEach{event ->
            var newEvent = RemoteEvent()
            newEvent.event = event.event
            newEvent.args = JsonObject()
            event.args.forEach { (key, value) ->
                newEvent.args.addProperty(key, value)
            }
            newEvent.deviceId = event.deviceId
            newEvent.timestamp = event.timestamp
        }

        return toRet
    }

    companion object {
        private const val TAG = "NOTIF"
    }
}
