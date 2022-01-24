package com.mahmoudsalah.goodsexpirydatetracker.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.mahmoudsalah.goodsexpirydatetracker.MainActivity
import com.mahmoudsalah.goodsexpirydatetracker.R


class MyNotification(val context:Context) {

    val CHANNEL_ID = "${R.string.app_name}"

    fun createNotificationChannel(intent: Intent?) {
        val name = intent?.getStringExtra("id")
        val date = intent?.getStringExtra("date")
        val notificationMa = NotificationManagerCompat.from(context)
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            0
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "${R.string.app_name}", importance)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)


// notificationId is a unique int for each notification that you must define
            notificationMa.notify(
                0,
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.expired)
                    .setContentTitle(name)
                    .setContentText("The Product is expire at ${date}")
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setContentIntent(pIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
            )
        } else {
            notificationMa.notify(
                0,
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.expired)
                    .setContentTitle(name)
                    .setContentText("The Product is expire at ${date}")
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setContentIntent(pIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
            )
        }
    }
}