package com.example.interlink

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.interlink.database.FavoritesDatabase
import com.example.interlink.receiver.ServerEventReceiver
import com.example.interlink.remote.DeviceRemoteDataSource
import com.example.interlink.remote.api.RetrofitClient
import com.example.interlink.repository.DeviceRepository
import com.example.interlink.repository.StoredEventRepository

class ApiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        collectServerEvents()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            with(NotificationManagerCompat.from(this)) {
                createNotificationChannel(channel)
            }
        }
    }

    private fun collectServerEvents() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ServerEventReceiver::class.java)

        var pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE)
        if (pendingIntent != null)
            alarmManager.cancel(pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.currentThreadTimeMillis(),
            INTERVAL,
            pendingIntent
        )
        Log.d(TAG, "Alarm set every $INTERVAL millis")
    }

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)


    val database = FavoritesDatabase.getDatabase(this)
    private val storedEventDao = database.storedEventDao()
    val eventRepository: StoredEventRepository
        get() = StoredEventRepository(storedEventDao)

    companion object {
        const val TAG = "NOTIF"
        const val CHANNEL_ID = "device"
        const val INTERVAL: Long = 1000 * 60
    }
}