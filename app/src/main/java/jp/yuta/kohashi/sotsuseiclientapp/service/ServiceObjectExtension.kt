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

interface ServiceObjectExtension<T : Any> {

    /**
     * サービスが起動しているか
     */
    fun isRunning(): Boolean {
        val am = App.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val listServiceInfo = am.getRunningServices(Integer.MAX_VALUE)
        return listServiceInfo.any { it.service.className == ((this as Object).`class` as Class<T>).name }
    }

    /**
     * サービス起動
     * 戻り値は起動に成功かすでに起動しているか
     */
    fun start(): ServiceStateResult {
        return if (isRunning()) {
            App.context.startService(Intent(App.context, ((this as Object).`class` as Class<T>).javaClass))
            ServiceStateResult.SUCCESS_RUN
        } else {
            ServiceStateResult.ALREADY_RUNNING
        }
    }

    /**
     * サービス停止
     * 戻り値はすでにに停止しているかどうか
     */
    fun stop(): ServiceStateResult {
        return if (isRunning()) {
            App.context.stopService(Intent(App.context, ((this as Object).`class` as Class<T>).javaClass))
            ServiceStateResult.SUCCESS_STOP
        } else {
            ServiceStateResult.ALREADY_STOPPED
        }
    }
}

enum class ServiceStateResult {
    ALREADY_RUNNING,
    SUCCESS_RUN,
    ALREADY_STOPPED,
    SUCCESS_STOP
}
