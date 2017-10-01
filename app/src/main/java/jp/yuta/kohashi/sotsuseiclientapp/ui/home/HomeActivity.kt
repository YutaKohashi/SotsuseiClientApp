package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import android.support.v4.app.Fragment
import android.view.View
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class HomeActivity:BaseActivity(){

    override fun setFragment(): Fragment?  = HomeFragment()

    override fun setContentViewFromRes(): Int?  = null
    override fun setContentViewFromView(): View? = null

}