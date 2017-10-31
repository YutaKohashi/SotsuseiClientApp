package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseDrawerActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.running.RunningFragment

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class HomeActivity : BaseDrawerActivity() {

    companion object : StartActivity<HomeActivity> {
        override fun start(activity: Activity) = super.start(activity, HomeActivity::class.java)
    }

    override val containerFragment: Fragment? = HomeFragment()

    override val menuItemFromRes: Int? = R.menu.menu_drawer

    private lateinit var mNavigationButton: View

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,false)
        mNavigationButton = defaultDrawerToggle()

        mContainerView.addView(mNavigationButton)
        mContainerView.bringChildToFront(mNavigationButton)
        mContainerView.requestLayout()

        setEvent()
    }

    override fun setEvent() {
        if (SotsuseiClientAppService.isRunning()) {
            Handler(Looper.getMainLooper()).postDelayed({
                addFragment(RunningFragment())
            }, 400)
        }

        mNavigationButton.setOnClickListener {
            openDrawer()
        }
    }

}