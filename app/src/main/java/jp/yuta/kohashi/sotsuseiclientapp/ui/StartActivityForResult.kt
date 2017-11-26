package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.app.Activity
import android.content.Intent

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 10 / 2017
 */
interface StartActivityForResult<T : Activity> {

    abstract fun start(activity: Activity)

    abstract val REQUEST_CODE:Int

    /**
     *  ex.
     *   companion object :StartActivity<LoginActivity> {
     *      override fun start(activity: Activity) = super.start(activity,LoginActivity::class.java)
     *  }
     */
    fun start(activity: Activity, clazz: Class<T>) = activity.startActivityForResult(Intent(activity, clazz),REQUEST_CODE)


}