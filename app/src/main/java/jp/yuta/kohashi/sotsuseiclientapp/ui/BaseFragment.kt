package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */
abstract class BaseFragment:Fragment(){

    @LayoutRes
    abstract fun RES_LAYOUT():Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(RES_LAYOUT(), container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    /**
     * ボタンのクリック処理を記述chrome
     */

    abstract fun setEvent()

}