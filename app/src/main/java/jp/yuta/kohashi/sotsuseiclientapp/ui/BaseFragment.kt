package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */
abstract class BaseFragment : Fragment() {

    abstract val mLayoutRes: Int
        @LayoutRes get

    inline fun<reified T:Activity> Fragment.activityStart(bundle: Bundle? = null){
        val intent = Intent(activity, T::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(mLayoutRes, container, false)
    }

    override fun onResume() {
        super.onResume()
        toolbarColor(R.color.colorPrimaryDark)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
    }

    protected fun popBackStack() {
        if (activity != null && activity.supportFragmentManager.backStackEntryCount != 0)
            activity.supportFragmentManager.popBackStack()
    }

    /**
     * 戻るボタンの押下イベント
     * @return backKey event through to activity
     */
    open fun onBackPressed(): Boolean = false


    /**
     * ボタンのクリック処理を記述chrome
     */

    abstract fun setEvent()

    protected fun toolbarColor(@ColorRes color:Int){
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ResUtil.color(color)
    }


}