package jp.yuta.kohashi.sotsuseiclientapp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.ui.login.LoginActivity


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 01 / 2018
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = MyFirebaseMessagingService::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "onMessageReceived")
        // Notification Messageを受信したことはRemoteMessage#getNotification()が非null値になることで判別
        if (remoteMessage != null && remoteMessage.notification != null) {
            Log.d(TAG, "get notification message")
            val title = remoteMessage.notification!!.title
            val message = remoteMessage.notification!!.body
            // TODO:通知
            if (title != null && message != null)
                sendNotification(title, message)
        }

        // dataメッセージングハンドリング
        if (remoteMessage?.data != null) {
            Log.d(TAG, "get data message")
            val data = remoteMessage.data
            val title = data["title"]
            val message = data["message"]

            if (title != null && message != null)
            // show notification
                sendNotification(title, message)
        }
    }

    /**
     * 通知を表示するメソッド
     */
    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}