package jp.yuta.kohashi.sotsuseiclientapp.ui.running

import android.os.Bundle
import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.service.ServiceStateResult
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.ToastHelper

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */
class RunningActivity : BaseActivity() {

    override val fragment: Fragment?
        get() = RunningFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (SotsuseiClientAppService.start()) {
            ServiceStateResult.ALREADY_RUNNING -> ToastHelper.alreadyRunningService()
            ServiceStateResult.SUCCESS_RUN -> ToastHelper.runService()
        }
    }

    override fun setEvent() {
    }

}