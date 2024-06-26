package com.ksc.geotasker.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

import android.os.Build

class LocalNotification :Application() {
    val CHANNEL_NAME = "DEFAULT_CHANNEL_NAME"
    val CHANNEL_ID = "DEFAULT_CHANNEL_ID"
    override fun onCreate() {
        super.onCreate()
        createDefaultNotificationChannel()
    }
    fun createDefaultNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }



}