package jp.yuta.kohashi.sotsuseiclientapp.ui

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 15 / 10 / 2017
 */
interface StartActivity2<T>{

    fun start(){
        val c = Class.forName("")
        val cons = c.getConstructor()
        val `object` = cons.newInstance()

    }
}