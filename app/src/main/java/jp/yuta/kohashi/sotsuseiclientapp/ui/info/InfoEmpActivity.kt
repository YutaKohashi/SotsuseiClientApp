package jp.yuta.kohashi.sotsuseiclientapp.ui.info

import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity

/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 15 / 01 / 2018
 */
class InfoEmpActivity :BaseActivity(){

    override val fragment: Fragment?
        get() = InfoEmpFragment()

    override fun setEvent() {

    }
}