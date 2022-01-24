package com.mahmoudsalah.goodsexpirydatetracker.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class MyAlarm(val context: Context) {
    fun setAlarm(name: String, date: String, dateInMilli: Date) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val intent = Intent(context, MyReceiver::class.java).let { intent ->
            intent.putExtra("id", name)
            intent.putExtra("date", date)
            PendingIntent.getBroadcast(context, name.hashCode(), intent, 0)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateInMilli.time,
                intent
            )
        } else {
            alarmManager?.set(
                AlarmManager.RTC_WAKEUP,
                dateInMilli.time,
                intent
            )
        }
    }
}