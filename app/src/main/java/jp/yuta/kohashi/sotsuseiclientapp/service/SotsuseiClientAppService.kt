package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import jp.yuta.kohashi.sotsuseiclientapp.App
import android.widget.Toast
import android.app.ActivityManager
import android.content.Context


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class SotsuseiClientAppService:Service(){

    companion object {

        val ALREADY_RUNNING:String = "already_running"
        val SUCCESS_RUN:String = "success_run"
        val ALREADY_STOPPED = "already_stopped"
        val SUCCESS_STOP = "success_stop"

        fun isRunning():Boolean{
            // サービスが実行中かチェック
            val am = App.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val listServiceInfo = am.getRunningServices(Integer.MAX_VALUE)
            var found = false
            for (curr in listServiceInfo) {
                // クラス名を比較
                if (curr.service.className.equals(SotsuseiClientAppService::class.java!!.getName())) {
                    // 実行中のサービスと一致
                    Toast.makeText(this, "サービス実行中", Toast.LENGTH_LONG).show()
                    found = true
                    break
                }
            }
        }

        fun start():String{
            return if(!isRunning()){
                val intent = Intent(App.context, SotsuseiClientAppService.javaClass)
                App.context.startService(intent)
                SUCCESS_RUN
            } else {
                ALREADY_RUNNING
            }
        }

        fun stop():String{
            return if(isRunning()){
                // Serviceの停止
                val intent = Intent(App.context, SotsuseiClientAppService.javaClass)
                App.context.stopService(intent)
                SUCCESS_STOP
            } else {
                ALREADY_STOPPED
            }
        }
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    /**
     * 使用しない
     */
    override fun onBind(p0: Intent?): IBinder?  = null

}