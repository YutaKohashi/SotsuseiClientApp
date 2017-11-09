package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */
abstract class BaseDialogFragment:DialogFragment(){
    private val TAG = BaseDialogFragment::class.java.simpleName
    private var requestCode: Int = 1000

    /**
     * 起動元のfragmentかActivityが入る
     */
    private var mTarget: Any? = null

    /**
     * レイアウトリソース
     */
    abstract open var layoutRes:Int
        get


//    abstract open  interface Callback

    companion object {
        private val KEY_REQUEST_CODE: String = "request_code"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        isCancelable = false
        return inflater!!.inflate(layoutRes, container, false)
    }


    fun show() {
        mTarget?.let { target ->
            if (target is AppCompatActivity) show(target)
            else if (target is Fragment) show(target)
        }
        mTarget = null
    }

    /**
     * show from activity
     * @param from extends BaseActivity and implements Callback
     */
    private fun show(from: BaseActivity) {
        show(from.supportFragmentManager, TAG)
    }

    /**
     * show from fragment
     */
    private fun show(from: BaseFragment) {
        setTargetFragment(from, requestCode)
        show(from.fragmentManager, TAG)
    }

    abstract class  Builder <R : BaseDialogFragment>protected constructor(){

        var requestCode: Int = 1000
        var activity: BaseActivity? = null
        var parentFragment: BaseFragment? = null

        constructor(init: Builder<R>.() -> Unit) : this() {
            init()
        }

        fun requestCode(action: Builder<R>.() -> Int) = apply { requestCode = action() }
        fun activity(action: Builder<R>.() -> BaseActivity)  = apply { activity = action() }
        fun parentFragment(action: Builder<R>.() -> BaseFragment) = apply { parentFragment = action() }

        abstract fun build():R

        protected fun setTarget(bundle:Bundle,fragment:BaseDialogFragment){
            if (activity != null) {
                fragment.mTarget = activity
                bundle.putInt(KEY_REQUEST_CODE, requestCode)
            } else {
                fragment.mTarget = parentFragment
            }
        }

    }
}