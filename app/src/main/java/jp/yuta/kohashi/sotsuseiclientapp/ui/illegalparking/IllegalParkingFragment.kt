package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.view.CameraView
import jp.yuta.kohashi.sotsuseiclientapp.utils.DisplayUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil
import kotlinx.android.synthetic.main.fragment_illegal_parking.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 08 / 11 / 2017
 */
class IllegalParkingFragment:BaseFragment(){

    override val mLayoutRes: Int = R.layout.fragment_illegal_parking

    private lateinit var mCameraView:CameraView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        mCameraView = CameraView(context)
        return v
    }

    override fun setEvent() {

        mCameraView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
        cameraViewContainer.addView(mCameraView)

//        closeButton.setOnClickListener{
//            activity.finish()
//        }
    }

    override fun onResume() {
        super.onResume()
        mCameraView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCameraView.onPause()
    }



}