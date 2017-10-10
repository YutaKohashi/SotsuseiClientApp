package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.app.Activity
import android.content.Intent

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 10 / 2017
 */
interface StartActivity<T: Any> {

    fun start(activity: Activity){
        activity.startActivity(Intent(activity,(this as java.lang.Object).`class` as Class<T>))
    }
}