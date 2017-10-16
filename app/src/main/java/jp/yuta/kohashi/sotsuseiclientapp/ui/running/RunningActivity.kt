package jp.yuta.kohashi.sotsuseiclientapp.ui.running

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.service.StateResult
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.ToastHelper

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */
class RunningActivity : BaseActivity() {

    companion object : StartActivity {
        override fun start(activity: Activity)  = activity.startActivity(Intent(activity,RunningActivity::class.java))
    }

    override val fragment: Fragment?
        get() = RunningFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (SotsuseiClientAppService.start(SotsuseiClientAppService::class.java)) {
            StateResult.ALREADY_RUNNING -> ToastHelper.alreadyRunningService()
            StateResult.SUCCESS_RUN -> ToastHelper.runService()
            else -> { }
        }
    }

    override fun setEvent() {
    }

}