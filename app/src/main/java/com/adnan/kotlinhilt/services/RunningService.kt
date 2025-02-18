package com.adnan.kotlinhilt.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.adnan.kotlinhilt.screens.activities.MainActivity
import java.text.SimpleDateFormat
import java.util.Locale

class RunningService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ServiceState.STARTED.name -> {

                // Start the service
                start()
            }
            ServiceState.STOPPED.name -> {
                // Stop the service
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

     fun start() {

         // Intent to launch MainActivity when notification is clicked
         val intent = Intent(this, MainActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         val pendingIntent = PendingIntent.getActivity(
             this,
             0,
             intent,
             PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Ensure compatibility with Android 12+
         )


        // Start the service
        val notification = NotificationCompat.Builder(this, "1")
            .setContentTitle("Service is running ${getCurrentTime()}")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent) // just to reopen the application
            .build()
        startForeground(1, notification)

    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        return sdf.format(System.currentTimeMillis())
    }
    enum class ServiceState {
        STARTED,
        STOPPED
    }
}