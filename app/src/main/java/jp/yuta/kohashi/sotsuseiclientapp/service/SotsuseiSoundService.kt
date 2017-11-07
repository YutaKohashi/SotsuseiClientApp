package jp.yuta.kohashi.sotsuseiclientapp.service

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import jp.yuta.kohashi.sotsuseiclientapp.R

@SuppressLint("Registered")
/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 26 / 10 / 2017
 */

class SotsuseiSoundService : BaseService() {
    private val TAG = SotsuseiSoundService::class.java.simpleName
    enum class ActionState(val num: Int) {

        ACTION_START(0),
        ACTION_STOP(1),
        ACTION_KILL(2);

        companion object {
            fun from(findValue: Int): ActionState = ActionState.values().first { it.num == findValue }
        }
    }

    inner class WakeHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "handleMessage  msg = " + msg.toString())
            when (ActionState.from(msg.what)) {
                ActionState.ACTION_START -> doPlayback()
                ActionState.ACTION_STOP -> stopPlayback()
                ActionState.ACTION_KILL -> stopService()
                else -> {
                }
            }
        }
    }

    companion object : ServiceExtension<SotsuseiSoundService> {
        override fun isRunning(): Boolean = super.isRunning(SotsuseiSoundService::class.java)
        override fun start(): StateResult = super.start(SotsuseiSoundService::class.java)
        override fun stop(): StateResult = super.stop(SotsuseiSoundService::class.java)
    }

    private lateinit var mHandler: WakeHandler
    private lateinit var mLooper: Looper
    private var mMediaPlayer: MediaPlayer? = null


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        val back = HandlerThread("background")
        back.start()

        mLooper = back.looper
        mHandler = WakeHandler(mLooper)

//        if(shouldShowNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
//        mHandler.dispatchMessage(mHandler.obtainMessage( ActionState.ACTION_START.num))
        actionStart()
//        return START_REDELIVER_INTENT
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        actionKill()
        mLooper.quit()
    }

    private fun actionStart(){
        mHandler.dispatchMessage(mHandler.obtainMessage( ActionState.ACTION_START.num))
    }

    private fun actionKill(){
        mHandler.dispatchMessage(mHandler.obtainMessage( ActionState.ACTION_KILL.num))
    }

    private fun doPlayback() {
        Log.d(TAG, "doPlayback")
        mMediaPlayer?.release()
        mMediaPlayer = null

        mMediaPlayer = MediaPlayer.create(this, R.raw.empty)
        mMediaPlayer?.isLooping = true
        mMediaPlayer?.setWakeMode(this, ActionState.ACTION_STOP.num)
        mMediaPlayer?.start()
    }

    private fun stopPlayback() {
        Log.d(TAG, "stopPlayback")
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    private fun stopService() {
        Log.d(TAG, "stopService")
        stopForeground(true)
        stopSelf()
    }

    private class SoundServiceException : Exception()
}