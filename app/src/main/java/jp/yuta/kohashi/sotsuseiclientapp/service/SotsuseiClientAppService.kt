package jp.yuta.kohashi.sotsuseiclientapp.service

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import jp.yuta.kohashi.sotsuseiclientapp.receiver.MediaControlReceiver


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService : BaseService() {
    private val TAG = SotsuseiClientAppService.javaClass.simpleName

    private var mAudioManager: AudioManager? = null
    private var mTmpVolumeLevel = 0
    private var mTmpRingMode: Int = AudioManager.RINGER_MODE_NORMAL


    /**
     * broadcastreceiverのイベント処理
     */
    private var invokeFlg = true
    private val invokeAction: () -> Unit = {
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, if (mTmpVolumeLevel == 0) 1 else mTmpVolumeLevel, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        if (invokeFlg) {
            Log.d(TAG, "invokeAction")
            Toast.makeText(applicationContext, "invokeAction", Toast.LENGTH_SHORT).show()
            invokeFlg = false
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(50);
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
        mTmpVolumeLevel = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
        if (mTmpVolumeLevel == 0) mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
//        return START_REDELIVER_INTENT
        mTmpRingMode = mAudioManager?.ringerMode ?: AudioManager.RINGER_MODE_NORMAL

        Log.d(TAG, "mTmpVolumeLevel = " + mTmpVolumeLevel.toString())
        Log.d(TAG, "mTmpRingMode    = " + mTmpRingMode.toString())

        if (mTmpRingMode != AudioManager.RINGER_MODE_NORMAL) mAudioManager?.ringerMode = AudioManager.RINGER_MODE_NORMAL

        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        unregisterReceiver()
        Log.d(TAG, "mAudioManager?.getStreamVolume = " + mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC).toString())
        Log.d(TAG, "mAudioManager?.ringerMode      = " + mAudioManager?.ringerMode.toString())
        if (mTmpVolumeLevel == 0) mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, mTmpVolumeLevel, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        mAudioManager?.ringerMode = mTmpRingMode


        Log.d(TAG, "mTmpVolumeLevel = " + mTmpVolumeLevel.toString())
        Log.d(TAG, "mTmpRingMode    = " + mTmpRingMode.toString())
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