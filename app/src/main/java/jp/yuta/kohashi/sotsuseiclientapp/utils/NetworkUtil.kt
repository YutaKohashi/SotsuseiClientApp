package jp.yuta.kohashi.sotsuseiclientapp.utils

import android.content.Context
import android.net.ConnectivityManager
import jp.yuta.kohashi.sotsuseiclientapp.App


/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 21 / 01 / 2018
 */
object NetworkUtil{

    /**
     * インターネットに接続しているか
     * @return
     */
    fun isConnectNetwork(): Boolean {
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info?.isConnected ?: false
    }

}