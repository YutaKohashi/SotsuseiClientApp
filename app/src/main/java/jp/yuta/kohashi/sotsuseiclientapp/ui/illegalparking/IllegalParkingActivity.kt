package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.app.Activity
import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseToolbarActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivity

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */

class IllegalParkingActivity:BaseActivity(){

    override val fragment: Fragment = IllegalParkingFragment()


    companion object : StartActivity<IllegalParkingActivity> {
        override fun start(activity: Activity) = super.start(activity,IllegalParkingActivity::class.java)
    }


    override fun setEvent() {

    }

}