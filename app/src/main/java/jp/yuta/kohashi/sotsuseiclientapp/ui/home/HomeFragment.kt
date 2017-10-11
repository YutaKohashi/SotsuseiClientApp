package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.running.RunningActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class HomeFragment:BaseFragment(){

    override val sLayoutRes: Int
        get() = R.layout.fragment_home


    override fun setEvent() {
        startButton.setOnClickListener {
            RunningActivity.start(activity)
        }
    }


}