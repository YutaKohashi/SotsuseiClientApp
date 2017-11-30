package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.hardware.camera2.CameraCaptureSession
import android.os.Bundle
import android.view.View
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseDialogFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.view.CameraView
import kotlinx.android.synthetic.main.diag_fragment_illegalparking.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */
class IllegalParkingDialogFragment : BaseDialogFragment() {
    override var layoutRes: Int = R.layout.diag_fragment_illegalparking

    override var NO_TITLE: Boolean = true

    interface Callback:BaseCallback{
        fun onPositive()
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

        val plate:Plate = arguments.getParcelable(KEY_PLATE)
        numberTextView.text = plate.number.toString()
        imageView.setImageBitmap(plate.bmp)
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    class Builder private constructor() {

        var activity: BaseDialogFragment? = null
        var parentFragment: BaseFragment? = null
        var number:Int = -1
        var plate:Plate? = null
        var requestCode: Int = 1000

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        fun requestCode(action: Builder.() -> Int) = apply { requestCode = action() }
        fun <T> activity(action: Builder.() -> T)  where T : BaseDialogFragment, T : Callback = apply { activity = action() }
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
                bundle.putInt(KEY_NUMBER,number)
                bundle.putParcelable(KEY_PLATE,plate)
                it.arguments = bundle
            }
        }
    }

}