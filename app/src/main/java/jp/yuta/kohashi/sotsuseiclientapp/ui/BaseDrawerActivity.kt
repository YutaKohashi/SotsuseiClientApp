package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import jp.yuta.kohashi.sotsuseiclientapp.utils.Util

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 30 / 10 / 2017
 */
abstract class BaseDrawerActivity : BaseActivity() {
    private val TAG = BaseDrawerActivity::class.java.simpleName

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mContainerView: FrameLayout
    private lateinit var mNavigationView: NavigationView

    /**
     * メニュー項目
     */
    @MenuRes
    open protected val menuItemFromRes: Int? = null

    /**
     * レイアウトリソースを設置するとき
     */
    @LayoutRes
    open protected val headerViewFromRes: Int? = null

    /**
     * Viewを設置するとき
     */
    open protected val headerViewFromView: View? = null

    /**
     * conteinerviewにfragmentをセット
     */
    /**
     * フラグメントを設置するとき
     */
    open protected val containerFragment: Fragment? = null

    /**
     * レイアウトリソースを設置するとき
     */
    @LayoutRes
    open protected val containerViewFromRes: Int? = null

    /**
     * Viewを設置するとき
     */
    open protected val containerViewFromView: View? = null


    /**
     * NavigationViewを適用したviewgroup
     */
    override val contentViewFromView: View? by lazy { createContentView() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        containerViewFromRes?.let { mContainerView.addView(Util.layoutRes2View(this, it, mContainerView, true)) }
        containerViewFromView?.let { mContainerView.addView(it) }
        containerFragment?.let { supportFragmentManager.beginTransaction().apply { add(mContainerView.id, it) }.commit() }
    }

    override fun setEvent() {

    }

    /**
     * create layout by navigation drawer
     */
    private fun createContentView(): View {

        mDrawerLayout = DrawerLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        mContainerView = FrameLayout(this).apply {
            id = View.generateViewId()
            layoutParams = DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT)
        }

        mNavigationView = NavigationView(this).apply {
            layoutParams = DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.MATCH_PARENT).also { params ->
                params.gravity = Gravity.START
            }
            fitsSystemWindows = true
            headerViewFromRes?.let { addHeaderView(Util.layoutRes2View(this@BaseDrawerActivity, it, this)) }
            headerViewFromView?.let { addHeaderView(it) }
            menuItemFromRes?.let { inflateMenu(it) }
        }

        mDrawerLayout.addView(mContainerView)
        mDrawerLayout.addView(mNavigationView)

        return mDrawerLayout
    }
}
