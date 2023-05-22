package com.example.happytohelp.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.happytohelp.MainActivity
import com.example.happytohelp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FCMService", "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.v("FCMService", "From: ${message.from}")

        if (message.data.isNotEmpty()) {
            Log.v("FCMService", "Message data payload: ${message.data}")
        }

        // message.notification?.let {
        Log.v("FCMService", "Message Notification Body: ${message.data}")
        showNotification(message)
        // }
    }

    private fun showNotification(message: RemoteMessage) {

        val channelID = "99"
        val channelName = "FCMMessage"

        val notificationTitle = message.data["title"];
        val notificationBody = message.data["body"];

        //val notificationTitle = message.notification!!.title;
        //val notificationBody = message.notification!!.body;

        Log.v("FCMService", "notificationTitle: $notificationTitle")
        Log.v("FCMService", "notificationMessage: $notificationBody")

        /*val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "app://com.example.happy/text_message=$notificationMessage".toUri()
            *//*,
            this,
            MainActivity::class.java*//*
        )
        Log.v("FCMService", "showNotification : Intent Created")
        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }*/

        //Create Intent it will be launched when user tap on notification from status bar.
        val intent = Intent(this, MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.putExtra("title", notificationTitle)
        intent.putExtra("body", notificationBody)

        // it should be unqiue when push comes.
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, requestCode, intent, FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        val notificationManager = NotificationManagerCompat.from(this) as NotificationManagerCompat

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.v("FCMService", "showNotification : Channel Created")
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        //.addAction(0,"ACTION_VIEW", deepLinkPendingIntent)

        //notificationManager.notify(Random.nextInt(), notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(100, notification.build())
            } else {
                return
            }
        } else {
            notificationManager.notify(100, notification.build())
        }
    }
}