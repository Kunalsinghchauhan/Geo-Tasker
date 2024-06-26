package com.ksc.geotasker.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import com.ksc.geotasker.R
import com.ksc.geotasker.ui.fragment.HomeFragment
import com.ksc.geotasker.utils.LocalNotification

class TaskBroadcastReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: NotificationCompat.Builder
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                Log.d("TaskBroadcastReceiver", "Airplane mode changed")
                Toast.makeText(context, "Airplane mode changed", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_POWER_CONNECTED -> {
                Log.d("TaskBroadcastReceiver", "Power connected")
                Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_PASTE -> {
                Log.d("TaskBroadcastReceiver", "Paste")
                Toast.makeText(context, "Paste Kar Dia Bhai", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d("TaskBroadcastReceiver", "Power disconnected")
                Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_HEADSET_PLUG -> {
                Log.d("TaskBroadcastReceiver", "Headset plugged/unplugged")
                Toast.makeText(context, "Headset plugged/unplugged", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_SCREEN_OFF -> {
                Log.d("TaskBroadcastReceiver", "Screen off")
                Toast.makeText(context, "Screen off", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_SCREEN_ON -> {
                Log.d("TaskBroadcastReceiver", "Screen on")
                Toast.makeText(context, "Screen on", Toast.LENGTH_SHORT).show()
            }

            Intent.ACTION_TIME_TICK -> {
                Log.d("TaskBroadcastReceiver", "Time tick")
                Toast.makeText(context, "Time tick", Toast.LENGTH_SHORT).show()
            }
        }
    }
}