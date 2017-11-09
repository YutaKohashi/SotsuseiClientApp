package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.os.Bundle
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseDialogFragment

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 11 / 2017
 */
class IllegalParkingDialogFragment : BaseDialogFragment() {
    override var layoutRes: Int = 0

    interface Callback{
        fun onSuccess()
        fun onCallback()
    }

    class Builder : BaseDialogFragment.Builder<IllegalParkingDialogFragment>() {

        override fun build(): IllegalParkingDialogFragment {

            return IllegalParkingDialogFragment().also {
                val bundle = Bundle()
                setTarget(bundle, it)

                it.arguments = bundle
            }
        }

    }

}