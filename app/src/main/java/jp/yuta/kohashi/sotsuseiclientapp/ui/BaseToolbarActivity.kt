package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toolbar

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 08 / 11 / 2017
 */
abstract class BaseToolbarActivity:BaseActivity(){

    private lateinit var mToolbar:Toolbar

    protected open var title:String = ""

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,false)

        mToolbar = Toolbar(this)
        mRootView?.addView(mToolbar,0)
        setActionBar(mToolbar)
        mToolbar.title = title
        setEvent()
    }
}