package jp.yuta.kohashi.sotsuseiclientapp.service

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

    fun isRunning(){

    }

    fun start(){

    }

    fun stop(){

    }
}