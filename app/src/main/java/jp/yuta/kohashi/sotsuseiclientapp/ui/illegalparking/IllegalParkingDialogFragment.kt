package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.View
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseDialogFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil
import kotlinx.android.synthetic.main.diag_fragment_illefalparking_01.*
import java.util.*


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */
class IllegalParkingDialogFragment : BaseDialogFragment(), View.OnClickListener {
    private val TAG = IllegalParkingDialogFragment::class.java.simpleName

    //    override var layoutRes: Int = R.layout.diag_fragment_illegalparking
    override var layoutRes: Int = R.layout.diag_fragment_illefalparking_01

    override var NO_TITLE: Boolean = true

    private var mCallback:Callback? = null

    private val mColorContainerIds by lazy {
        mutableListOf<@LayoutRes Int>().apply {
            add(R.id.container01) // 白
            add(R.id.container02) // 黄色
            add(R.id.container03) // 緑
            add(R.id.container04) //　黒
            add(R.id.container05)
        }
    }

//    data class NumberPlate(val shiyohonkyochi: String, val bunruibango: String, val jigyoyohanbetsumoji: String, val ichirenshiteibango: String, val cartype: Int, val colortype: Int, val makertype: Int, val comment: String, val datetime: String)

    interface Callback : BaseCallback {
        fun onPositive(numberPlate: Model.NumberPlate)
        fun onCancel()
    }

    companion object {
        protected val KEY_REQUEST_CODE: String = "request_code"
        protected val KEY_NUMBER: String = "number"
        protected val KEY_PLATE: String = "plate"
        fun create(action: Builder.() -> Unit): IllegalParkingDialogFragment = IllegalParkingDialogFragment.Builder(action).build()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        val plate: Plate = arguments.getParcelable(KEY_PLATE)
        // 取得したナンバー情報をviewに適用
        numberPlateView.applyNumber(plate.number)

        mColorContainerIds.forEach { id -> view?.findViewById<View>(id)?.setOnClickListener(this@IllegalParkingDialogFragment) }

        closeButton.setOnClickListener { dismiss() }

        // 決定ボタン
        // TODO cartype と makertypeを指定していない
        // humanid, imageid,は後の機能としておいておく
        defineButton.setOnClickListener {
            numberPlateView.number?.let { number ->
                val numberPlate = Model.NumberPlate(
                        PrefUtil.empId,
                        PrefUtil.storeId,
                        "",
                        "",
                        numberPlateView.honkyochi,
                        numberPlateView.bunruiNum,
                        numberPlateView.hanbetsuMoji,
                        number,
                        0,
                        selectedColorType(),
                        0,
                        "",
                        Date() // 現在時刻を設定
                )
                mCallback?.onPositive(numberPlate)
            } ?:mCallback?.onCancel()
            dismiss()
        }
    }

    /**
     * onAttach共通処理
     */
    override fun onAttachContext(context: Context) {
        Log.d(TAG, "onAttach")
        var callback: Any? = targetFragment
        if (callback == null) {
            callback = context
            if (callback == null || callback !is Callback) {
                throw IllegalStateException("failure:: can't get 'Callback' in onAttach")
            }
        }
        mCallback = callback as Callback
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
        mCallback = null
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    @IdRes
    private var selectedView:Int? = null

    private fun selectedColorType():Int{
        return selectedView?.let { mColorContainerIds.indexOf(it) } ?:0
    }

    /**
     * click color view
     */
    override fun onClick(v: View?) {
        selectedView = v?.id
        v?.setBackgroundColor(ResUtil.color(android.R.color.black))

        // 押下されたボタン以外の背景を初期化
        mColorContainerIds.filter { v?.id != it }.forEach { id ->
            v?.rootView?.findViewById<View>(id)?.setBackgroundColor(ResUtil.color(android.R.color.white))
        }
    }

    class Builder private constructor() {

        var activity: BaseDialogFragment? = null
        var parentFragment: BaseFragment? = null
        var number: Int = -1
        var plate: Plate? = null
        var requestCode: Int = 1000

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        fun requestCode(action: Builder.() -> Int) = apply { requestCode = action() }
        fun <T> activity(action: Builder.() -> T) where T : BaseDialogFragment, T : Callback = apply { activity = action() }
        fun <T> parentFragment(action: Builder.() -> T) where T : BaseFragment, T : Callback = apply { parentFragment = action() }
        fun number(action: Builder.() -> Int) = apply { number = action() }
        fun plate(action: Builder.() -> Plate) = apply { plate = action() }

        fun build(): IllegalParkingDialogFragment {

            return IllegalParkingDialogFragment().also {
                val bundle = Bundle()
                if (activity != null) {
                    it.mTarget = activity
                    bundle.putInt(KEY_REQUEST_CODE, requestCode)
                } else {
                    it.mTarget = parentFragment
                }
                bundle.putInt(KEY_NUMBER, number)
                bundle.putParcelable(KEY_PLATE, plate)
                it.arguments = bundle
            }
        }
    }

}