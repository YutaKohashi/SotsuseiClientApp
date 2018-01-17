package jp.yuta.kohashi.sotsuseiclientapp.ui.login

import android.os.Bundle
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.netowork.FirebaseHelper
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login_tenpo.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */
class LoginFragment:BaseFragment(){

    override val mLayoutRes: Int
        get() = R.layout.fragment_login_tenpo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setEvent() {
        loginButton.setOnClickListener {
            // TODO ログイン処理
//            HomeActivity.start(activity)
            activityStart<HomeActivity>()
            activity.finish()
        }
    }

}