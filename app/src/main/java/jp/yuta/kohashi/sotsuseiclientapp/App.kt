package jp.yuta.kohashi.sotsuseiclientapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
//import com.squareup.leakcanary.LeakCanary
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.AnprManager
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil

/**
 * Author : yutakohashi
 * Project name : TwitterLiteProject
 * Date : 25 / 07 / 2017
 */
@SuppressLint("StaticFieldLeak")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AnprManager.init()
        PrefUtil.setUp(context)
//        if (LeakCanary.isInAnalyzerProcess(this))return
//        LeakCanary.install(this)
    }

    companion object {
        lateinit var context: Context
            get
            private set
    }
}
