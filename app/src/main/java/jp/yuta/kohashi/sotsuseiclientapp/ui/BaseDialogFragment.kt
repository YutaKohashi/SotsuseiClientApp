package jp.yuta.kohashi.sotsuseiclientapp.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

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
    protected var mTarget: Any? = null

    /**
     * レイアウトリソース
     */
    abstract open var layoutRes:Int
        get


//    abstract open  interface Callback

    open var NO_TITLE:Boolean = false
        get

    private var mCallback:BaseCallback? = null


    companion object {

        protected val KEY_REQUEST_CODE: String = "request_code"
    }

    /**
     * コールバクするBaseInterface
     */
    interface  BaseCallback

    /**
     * レイアウト適用
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        isCancelable = false
        return inflater!!.inflate(layoutRes, container, false)
    }

    /**
     * 各種項目を設定
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if(NO_TITLE)dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    /**
     * clickイベントやCallback処理を記述
     */
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    /**
     * LOLLIPOP以上
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachContext(context)
    }

    /**
     * LOLLIPOP未満
     */
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return
        onAttachContext(activity)
    }

    /**
     * onAttach共通処理
     */
    open fun onAttachContext(context: Context) {
        Log.d(TAG, "onAttach")
        var callback: Any? = targetFragment
        if (callback == null) {
            callback = context
            if (callback == null || callback !is BaseCallback) {
                throw IllegalStateException("failure:: can't get 'Callback' in onAttach")
            }
        }
        mCallback = callback as BaseCallback
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
        mCallback = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
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
    private fun show(from: AppCompatActivity) {
        show(from.supportFragmentManager, TAG)
    }

    /**
     * show from fragment
     */
    private fun show(from: Fragment) {
        setTargetFragment(from, requestCode)
        show(from.fragmentManager, TAG)
    }

//
//    abstract class  Builder <R : BaseDialogFragment>protected constructor(){
//
//        var requestCode: Int = 1000
//        var activity: AppCompatActivity? = null
//        var parentFragment: Fragment? = null
//
//        constructor(init: Builder<R>.() -> Unit) : this() {
//            init()
//        }
//
//        fun requestCode(action: Builder<R>.() -> Int) = apply { requestCode = action() }
//        fun activity(action: Builder<R>.() -> BaseActivity)  = apply { activity = action() }
//        fun parentFragment(action: Builder<R>.() -> BaseFragment) = apply { parentFragment = action() }
//
//        abstract fun build():R
//
//        /**
//         * 返すDialogのインスタンスにtargetを設定するメソッド
//         */
//        protected fun setTarget(bundle:Bundle,fragment:BaseDialogFragment){
//            if (activity != null) {
//                fragment.mTarget = activity
//                bundle.putInt(KEY_REQUEST_CODE, requestCode)
//            } else {
//                fragment.mTarget = parentFragment
//            }
//        }
//
////        interface BuilderInterface<R : BaseDialogFragment>{
////            abstract fun create(action: Builder<R>.() -> Unit): R
////        }
//    }


    abstract  class  Builder<R:BaseDialogFragment>{
        abstract fun build():R
    }
}