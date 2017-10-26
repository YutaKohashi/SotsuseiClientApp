package jp.yuta.kohashi.sotsuseiclientapp.ui.login

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

 class LoginActivity:BaseActivity(){

    companion object :StartActivity<LoginActivity> {
        override fun start(activity: Activity) = super.start(activity,LoginActivity::class.java)
    }


    override val fragment: Fragment?
        get() = LoginFragment()

    override fun setEvent() {

    }

}
