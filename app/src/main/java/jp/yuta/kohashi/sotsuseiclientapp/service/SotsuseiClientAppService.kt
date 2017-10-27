package jp.yuta.kohashi.sotsuseiclientapp.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.util.Log
import jp.yuta.kohashi.sotsuseiclientapp.receiver.MediaControlReceiver
import android.os.Vibrator
import android.widget.Toast


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService : BaseService() {
    private val TAG = SotsuseiClientAppService.javaClass.simpleName

    private var mAudioManager: AudioManager? = null
    private var tmpVolumeLevel = 0

    private var invokeFlg = true

    /**
     * broadcastreceiverのイベント処理
     */
    private val invokeAction: () -> Unit = {
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, if (tmpVolumeLevel == 0) 1; else tmpVolumeLevel, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        if (invokeFlg) {
            Log.d(TAG, "invokeAction")
            Toast.makeText(applicationContext, "invokeAction", Toast.LENGTH_SHORT).show()
            invokeFlg = false
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(150);
            }
            Handler().postDelayed({
                invokeFlg = true
            }, 3000L)
        }
    }

    companion object : ServiceExtension<SotsuseiClientAppService> {
        override fun start(): StateResult = super.start(SotsuseiClientAppService::class.java)
        override fun stop(): StateResult = super.stop(SotsuseiClientAppService::class.java)
        override fun isRunning(): Boolean = super.isRunning(SotsuseiClientAppService::class.java)

        var sBroadcastReceiver: MediaControlReceiver? = null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        registerReceiver()
        SotsuseiSoundService.start()
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        tmpVolumeLevel = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
        if (tmpVolumeLevel == 0) mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        unregisterReceiver()
        if (tmpVolumeLevel == 0) mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, tmpVolumeLevel, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        SotsuseiSoundService.stop()
    }

    // region broadcast receiver

    private fun registerReceiver() {
        sBroadcastReceiver = MediaControlReceiver(invokeAction)
        val intentFilter = IntentFilter(Intent.ACTION_MEDIA_BUTTON)
        intentFilter.priority = 1000
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION")
        registerReceiver(sBroadcastReceiver, intentFilter)
    }

    private fun unregisterReceiver() {
        unregisterReceiver(sBroadcastReceiver)
    }

    // endregion

}