package edu.bluejack22_1.beestack

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.bluejack22_1.beestack.activities.MainActivity
import edu.bluejack22_1.beestack.view.Home


const val channelId = "notification_channel"
const val channelName = "edu.bluejack22_1.beestack"

class MyFirebaseMessagingService : FirebaseMessagingService(){

//    Generate the notification
//    Attach the notification created with the custom layout


    override fun onMessageReceived(remote: RemoteMessage) {
        super.onMessageReceived(remote)
        Log.d("test", "recieved message!");
        if(remote != null){
            generateNotification()
        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(): RemoteViews {
        val remoteView = RemoteViews("edu.bluejack22_1.beestack", R.layout.activity_main);
        return remoteView;
    }

    fun generateNotification(){
        val intent = Intent(this, MainActivity::class.java);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo_resize)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent);

        builder = builder.setContent(getRemoteView())
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

}