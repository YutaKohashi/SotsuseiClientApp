package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService:Service(){

    companion object :ServiceObjectExtension<SotsuseiClientAppService>

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 使用しない
     */
    override fun onBind(p0: Intent?): IBinder?  = null

}