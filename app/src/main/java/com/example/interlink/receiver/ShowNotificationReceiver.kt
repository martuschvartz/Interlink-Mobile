package com.example.interlink.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.interlink.ApiApplication
import com.example.interlink.MainActivity
import com.example.interlink.MyIntent
import com.example.interlink.R

class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == MyIntent.SHOW_NOTIFICATION) {
            val eventData: String? = intent.getStringExtra(MyIntent.EVENT_DATA)
            Log.d(TAG, "Show notification intent received {$eventData)")

            showNotification(context, eventData!!)
        }
    }

    private fun showNotification(context: Context, eventData: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MyIntent.EVENT_DATA, eventData)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val builder = NotificationCompat.Builder(context, ApiApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Interlink")
            .setContentText(eventData)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled())
                notificationManager.notify(eventData.hashCode(), builder.build())
        } catch (e: SecurityException) {
            Log.d(TAG, "Notification permission not granted $e")
        }
    }

    companion object {
        private const val TAG = "NOTIF"
    }
}