package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import jp.yuta.kohashi.sotsuseiclientapp.App

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 10 / 10 / 2017
 */

interface ServiceObjectExtension<T : BaseService> {
    
//    private fun javaClass():Class<T> = ((this as Object).`class` as Class<T>)
    
    /**
     * サービスが起動しているか
     */
    fun isRunning(clazz:Class<T>): Boolean {
        val am = App.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val listServiceInfo = am.getRunningServices(Integer.MAX_VALUE)
        val name:String = clazz.name
        return listServiceInfo.any { it.service.className == clazz.name }
    }

    /**
     * サービス起動
     * 戻り値は起動に成功かすでに起動しているか
     */
    fun start(clazz:Class<T>): StateResult {
        return if (!isRunning(clazz)) {
            App.context.startService(Intent(App.context, clazz))
            StateResult.SUCCESS_RUN
        } else {
            StateResult.ALREADY_RUNNING
        }
    }

    /**
     * サービス停止
     * 戻り値はすでにに停止しているかどうか
     */
    fun stop(clazz:Class<T>): StateResult {
        return if (isRunning(clazz)) {
            App.context.stopService(Intent(App.context, clazz))
            StateResult.SUCCESS_STOP
        } else {
            StateResult.ALREADY_STOPPED
        }
    }
}

enum class StateResult {
    ALREADY_RUNNING,
    SUCCESS_RUN,
    ALREADY_STOPPED,
    SUCCESS_STOP
}

