package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivity

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class HomeActivity : BaseActivity() {


    companion object : StartActivity<HomeActivity> {
        override fun start(activity: Activity) = super.start(activity, HomeActivity::class.java)
    }

    override val fragment: Fragment?
        get() = HomeFragment()


    override fun setEvent() {


    }
}