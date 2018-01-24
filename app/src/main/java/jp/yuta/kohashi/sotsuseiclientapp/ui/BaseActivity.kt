package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil

/**
 * Activityを作る際は必ず継承してください
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * フラグメントを設置するとき
     */
    open protected val fragment: Fragment? = null

    /**
     * レイアウトリソースを設置するとき
     */
    @LayoutRes
    open protected val contentViewFromRes: Int? = null

    /**
     * Viewを設置するとき
     */
    open protected val contentViewFromView: View? = null

    /**
     *  Keep screen on flag
     */
    open protected val KEEP_SCREEN_ON = false

    /**
     *  hide titlebar
     */
    open protected val HIDE_STATUSBAR = false

    /**
     * フラグメント設置時にrootとなるViewGroup
     */
    protected var mRootView: LinearLayout? = null
    /**
     * フラグメント設置時にフラグメントのコンテナとなるViewGroup
     */
    protected var mFragmentContainer:FrameLayout? = null

    /**
     * setEventメソッドを実行するか
     */
    protected var isEvent: Boolean = true

    protected inline fun<reified T:Activity> Activity.activityStart(bundle: Bundle? = null){
        val intent = Intent(this, T::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        onCreate(savedInstanceState, true)
    }

    open fun onCreate(savedInstanceState: Bundle?, isEvent: Boolean) {
        super.onCreate(savedInstanceState)

        contentViewFromRes?.let { setContentView(it) }
        contentViewFromView?.let { setContentView(it) }
        fragment?.let {
            // フラグメントコンテナ作成
            mFragmentContainer = FrameLayout(this).apply {
                id = View.generateViewId()
                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            }

            // ルートViewGroup作成
            mRootView = LinearLayout(this).apply {
                id = View.generateViewId()
                layoutParams = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                orientation = LinearLayout.VERTICAL
                addView(mFragmentContainer)
            }

            setContentView(mRootView)
            supportFragmentManager.beginTransaction().apply { add(mFragmentContainer!!.id, it) }.commit()
        }
        if (isEvent) setEvent()
    }

    open fun replaceFragment(fragment: Fragment) {
        if (mFragmentContainer == null) return
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(mFragmentContainer!!.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    open fun addFragment(fragment: Fragment) {
        if (mFragmentContainer == null) return
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(mFragmentContainer!!.id, fragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        if (KEEP_SCREEN_ON) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        if (KEEP_SCREEN_ON) window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * 戻るボタンの押下イベント
     */
    override fun onBackPressed() {
        super.onBackPressed()
        currentFragment()?.let {
            /**
             * fragment側でfalseを返すとactivityのonbackpressedイベントは呼ばれない
             */
            if ((it as? BaseFragment)?.onBackPressed() == false) return
            else onBackPressed()
        } ?: super.onBackPressed()
    }

    protected fun currentFragment(): Fragment? {
        try {
            return supportFragmentManager.fragments?.filter { it != null && it.isVisible }?.get(0)
        }catch (e:Exception){
            return null
        }
    }

    /**
     * ボタンのクリック処理などを中心に記述
     */
    abstract fun setEvent()

    protected fun toolbarColor(@ColorRes color:Int){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ResUtil.color(color)
    }

    protected fun toolbarColorDefault(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ResUtil.color(R.color.colorPrimaryDark)
    }

    fun showSnackBar(text:String){
        contentViewFromView?.let {  Snackbar.make(it,text,Snackbar.LENGTH_SHORT).show()}?:
        mRootView?.let { Snackbar.make(it,text,Snackbar.LENGTH_SHORT).show() }?:
                findViewById<View>(android.R.id.content)?.let { Snackbar.make(it,text,Snackbar.LENGTH_SHORT).show() }
    }

    fun showSnackBar(text:String,startTime:Long){
        Handler(Looper.getMainLooper()).postDelayed({
            showSnackBar(text)
        }, startTime)
    }

}
