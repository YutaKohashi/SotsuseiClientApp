package jp.yuta.kohashi.sotsuseiclientapp.service

import android.content.ComponentName
import android.content.Intent
import android.media.AudioManager
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.util.Log
import jp.yuta.kohashi.sotsuseiclientapp.receiver.MediaControlReceiver


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService : BaseService() {
    private val TAG = SotsuseiClientAppService.javaClass.simpleName

    private var mAudioManager: AudioManager? = null
    private var mComponentName: ComponentName? = null
    private var mSession: MediaSession? = null

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
//        Log.d(TAG, "onStartCommand")
//        sBroadcastReceiver = MediaControlReceiver(invokeAction)
//        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        mComponentName = ComponentName(this,MediaControlReceiver::class.java)
//        mAudioManager?.registerMediaButtonEventReceiver(mComponentName)
//
//        val intentFilter = IntentFilter(Intent.ACTION_MEDIA_BUTTON)
//        intentFilter.priority = 1000
////        registerReceiver(sBroadcastReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
//        registerReceiver(sBroadcastReceiver, intentFilter)

        mSession = MediaSession(this, "TAG")

        mSession?.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS or MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS)

        val state = PlaybackState.Builder()
                .setActions(
                        PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PLAY_PAUSE or
                                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID or PlaybackState.ACTION_PAUSE or
                                PlaybackState.ACTION_SKIP_TO_NEXT or PlaybackState.ACTION_SKIP_TO_PREVIOUS)
//                .setState(PlaybackState.STATE_PLAYING, position, speed, SystemClock.elapsedRealtime())
                .build()
        mSession?.setPlaybackState(state)
        mSession?.setCallback(object : MediaSession.Callback() {
            override fun onMediaButtonEvent(mediaButtonIntent: Intent?): Boolean {
                Log.d(TAG, "onMediaButtonEvent")
                return super.onMediaButtonEvent(mediaButtonIntent)
            }
        })

        if (!(mSession?.isActive?: true)) mSession?.isActive = true

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSession?.isActive = false
//        unregisterReceiver(sBroadcastReceiver)
    }


}