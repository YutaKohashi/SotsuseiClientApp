package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import jp.yuta.kohashi.sotsuseiclientapp.App

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 10 / 10 / 2017
 */

interface ServiceObjectExtension<T: Any> {

    companion object {
        val ALREADY_RUNNING:String = "already_running"
        val SUCCESS_RUN:String = "success_run"
        val ALREADY_STOPPED = "already_stopped"
        val SUCCESS_STOP = "success_stop"
    }

    /**
     * サービスが起動しているか
     */
    fun isRunning():Boolean{
        val am = App.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val listServiceInfo = am.getRunningServices(Integer.MAX_VALUE)
        return listServiceInfo.any { it.service.className == ((this as Object).`class` as Class<T>).name }

    }

    /**
     * サービス起動
     * 戻り値は起動に成功かすでに起動しているか
     */
    fun start():String{
        return if(isRunning()){
            App.context.startService(Intent(App.context, ((this as Object).`class` as Class<T>).javaClass))
            SUCCESS_RUN
        } else {
            ALREADY_RUNNING
        }
    }

    /**
     * サービス停止
     * 戻り値はすでにに停止しているかどうか
     */
    fun stop():String{
        return if(isRunning()){
            App.context.stopService(Intent(App.context, ((this as Object).`class` as Class<T>).javaClass))
            SUCCESS_STOP
        } else {
            ALREADY_STOPPED
        }
    }
}