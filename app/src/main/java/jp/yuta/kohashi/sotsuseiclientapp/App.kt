package jp.yuta.kohashi.sotsuseiclientapp

import android.app.Application
import android.content.Context
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil

/**
 * Author : yutakohashi
 * Project name : TwitterLiteProject
 * Date : 25 / 07 / 2017
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        PrefUtil.setUp(context)
    }

    companion object {
        lateinit var context: Context
            get
            private set
    }
}
