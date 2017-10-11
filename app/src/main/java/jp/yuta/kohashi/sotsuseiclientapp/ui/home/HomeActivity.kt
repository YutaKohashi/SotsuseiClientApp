package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.login.LoginFragment

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class HomeActivity:BaseActivity(){


    companion object :StartActivity<HomeActivity>{

    }
    override val fragment: Fragment?
        get() = HomeFragment()


    override fun setEvent() {


    }
}