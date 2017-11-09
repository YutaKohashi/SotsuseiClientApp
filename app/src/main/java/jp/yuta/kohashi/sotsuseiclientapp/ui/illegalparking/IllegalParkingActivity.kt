package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.support.v4.app.Fragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseToolbarActivity

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */

class IllegalParkingActivity:BaseToolbarActivity(){

    override val fragment: Fragment = IllegalParkingFragment()

     override var title = "違法駐車"


    override fun setEvent() {

    }

}