package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Activityを作る際は必ず継承してください
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * フラグメントを設置するとき
     */
    abstract fun setFragment(): Fragment?

    /**
     * レイアウトリソースを設置するとき
     */
    @LayoutRes
    abstract fun setContentViewFromRes(): Int?

    /**
     * Viewを設置するとき
     */
    abstract fun setContentViewFromView(): View?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentViewFromRes()?.let { setContentView(it) }
        setContentViewFromView()?.let { setContentView(it) }
        setFragment()?.let {
            val rootView = FrameLayout(this)
            rootView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setContentView(rootView)
            supportFragmentManager.beginTransaction().apply { add(rootView.id, it) }.commit()
        }
    }

    /**
     * ボタンのクリック処理などを中心に記述
     */
    abstract fun setEvent()



}
