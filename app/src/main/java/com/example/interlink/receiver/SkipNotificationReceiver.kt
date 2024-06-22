package com.example.interlink.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.interlink.MyIntent

class SkipNotificationReceiver (private val deviceId: String) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(MyIntent.SHOW_NOTIFICATION)) {
            Log.d(TAG, "Skipping notification send ($deviceId)")
            abortBroadcast()
        }
    }

    companion object {
        private const val TAG = "NOTIF"
    }
}