package com.example.interlink.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.interlink.BuildConfig
import com.example.interlink.MyIntent
import com.example.interlink.R
import com.example.interlink.database.FavoritesDatabase
import com.example.interlink.database.StoredEvent
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
import com.example.interlink.remote.model.RemoteDeviceType
import com.example.interlink.repository.StoredEventRepository
import com.example.interlink.ui.devices.StoredEventEntryViewModelFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

class ServerEventReceiver : BroadcastReceiver() {

    private val gson = Gson()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Alarm received.")

        GlobalScope.launch(Dispatchers.IO) {
            val events = fetchEvents()
            if (events != null) {
                val database = FavoritesDatabase.getDatabase(context!!)
                val storedEventDao = database.storedEventDao()
                val repo = StoredEventRepository(storedEventDao)

                val remoteEvents = parseEvents(events)

                remoteEvents.forEach {
                    
                    val notifString = eventDescription(it, context)
                    Log.d(TAG, "Broadcasting send notification intent (${StoredEvent(name = it.device.name, description = notifString, timestamp = it.timestamp)})")
                    repo.insertEvent(StoredEvent(name = it.device.name, description = notifString, timestamp = it.timestamp))

                    val intent2 = Intent().apply {
                        action = MyIntent.SHOW_NOTIFICATION
                        `package` = MyIntent.PACKAGE
                        // Esto tendria que ser el evento
                        putExtra(MyIntent.EVENT_DATA, "${it.device.name}: $notifString")
                    }
                    Log.d(TAG, "Broadcasting send notification intent (${intent2})")

                    context.sendOrderedBroadcast(intent2, null)
                }
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
            newDev

        } else {
            Log.d(TAG, "No device found")
            null
        }
    }

    private fun parseEvents(events : List<EventData>) : List<RemoteEvent>{
        var toRet : List<RemoteEvent> = emptyList()
        events.forEach{event ->
            val newEvent = RemoteEvent()
            newEvent.event = event.event
            newEvent.args = JsonObject()
            event.args.forEach { (key, value) ->
                newEvent.args.addProperty(key, value)
            }
            newEvent.deviceId = event.deviceId
            newEvent.timestamp = event.timestamp
            newEvent.device = fetchDevice(event.deviceId)!!

            toRet = toRet + newEvent
        }

        toRet = filterEvents(toRet)
        return toRet
    }

    private fun filterEvents(eventList : List<RemoteEvent>) : List<RemoteEvent> {
        val toRet = eventList.filter{ event ->
            when(event.device.type){
                DeviceType.DOOR -> {
                    when (event.event) {
                        "statusChanged" -> true
                        "lockChanged" -> true
                        else -> false
                    }
                }
                DeviceType.ALARM -> event.event == "statusChanged"
                DeviceType.BLINDS-> event.event == "statusChanged"
                else -> false
            }
        }
        return toRet
    }

    private fun eventDescription(event: RemoteEvent, context: Context?) : String{
        var toRet : String = ""
        val argsObject = event.args.asJsonObject
        when(event.device.type){
            DeviceType.DOOR -> {
                when (event.event) {
                    "statusChanged" -> {
                        when(argsObject["newStatus"].asString){
                            "opened" -> toRet = context!!.getString(R.string.openedDoor)
                            "closed" -> toRet = context!!.getString(R.string.closedDoor)
                        }
                    }
                    "lockChanged" -> when(argsObject["newLock"].asString){
                        "locked" -> toRet = context!!.getString(R.string.lockedDoor)
                        "unlocked" -> toRet = context!!.getString(R.string.unlockedDoor)
                    }
                }
            }
            DeviceType.ALARM -> when(argsObject["newStatus"].asString){
                "armedStay" -> toRet= context!!.getString(R.string.armedAlarm)
                "armedAway" -> toRet = context!!.getString(R.string.armedAlarm)
                "disarmed" -> toRet = context!!.getString(R.string.disarmedAlarm)
            }
            DeviceType.BLINDS-> when(argsObject["newStatus"].asString){
                "opened" -> toRet = context!!.getString(R.string.openedBlinds)
                "closed" -> toRet = context!!.getString(R.string.closedBlinds)
                "opening" -> toRet = context!!.getString(R.string.openingBlinds)
                "closing" -> toRet = context!!.getString(R.string.closingBlinds)
            }

            else -> {}
        }

        return toRet
    }


    companion object {
        private const val TAG = "NOTIF"
    }
}
