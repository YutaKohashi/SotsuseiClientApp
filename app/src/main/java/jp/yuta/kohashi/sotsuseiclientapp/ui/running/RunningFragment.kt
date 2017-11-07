package jp.yuta.kohashi.sotsuseiclientapp.ui.running

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.service.StateResult
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.ToastHelper
import kotlinx.android.synthetic.main.fragment_running.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class RunningFragment : BaseFragment() {

    override val sLayoutRes: Int
        get() = R.layout.fragment_running

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        when (SotsuseiClientAppService.start()) {
            StateResult.ALREADY_RUNNING -> ToastHelper.alreadyRunningService()
            StateResult.SUCCESS_RUN -> ToastHelper.runService()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun setEvent() {
        /**
         * ストップボタン
         */
        stopButton.setOnClickListener {
            when (SotsuseiClientAppService.stop(SotsuseiClientAppService::class.java)) {
                StateResult.SUCCESS_STOP -> ToastHelper.stopService()
                StateResult.ALREADY_STOPPED -> ToastHelper.alreadyStopService()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                popBackStack()
            }, 400)
        }
    }

    /**
     * 戻るボタンの押下イベント
     */
    override fun onBackPressed():Boolean {
        activity.finish()
        return super.onBackPressed()
    }

}