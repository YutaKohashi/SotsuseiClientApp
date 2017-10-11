package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.support.v4.media.session.MediaButtonReceiver
import android.util.Log
import jp.yuta.kohashi.sotsuseiclientapp.receiver.MediaControlReceiver


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService : Service() {
    private val TAG = SotsuseiClientAppService.javaClass.simpleName

    /**
     * broadcastreceiverのイベント処理
     */
    private val invokeAction: () -> Unit = {
        Log.d(TAG, "invokeAction")
    }

    companion object : ServiceObjectExtension<SotsuseiClientAppService> {
        var sBroadcastReceiver: MediaControlReceiver? = null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sBroadcastReceiver = MediaControlReceiver(invokeAction)
        registerReceiver(sBroadcastReceiver, IntentFilter().apply { addAction(Intent.ACTION_MEDIA_BUTTON) })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(sBroadcastReceiver)
    }


    /**
     * 使用しない
     */
    override fun onBind(p0: Intent?): IBinder? = null

}