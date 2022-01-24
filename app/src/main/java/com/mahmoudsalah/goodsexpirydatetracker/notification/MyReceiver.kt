package com.mahmoudsalah.goodsexpirydatetracker.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
            context?.let {
                MyNotification(it).createNotificationChannel(intent)
            }
    }
}
