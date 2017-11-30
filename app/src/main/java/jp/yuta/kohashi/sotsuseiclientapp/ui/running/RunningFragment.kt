package jp.yuta.kohashi.sotsuseiclientapp.ui.running

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.service.StateResult
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.ToastHelper
import jp.yuta.kohashi.sotsuseiclientapp.ui.home.HomeActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.IllegalParkingActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.IllegalParkingDialogFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.NumberPlateScanActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.Plate
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil
import kotlinx.android.synthetic.main.fragment_running.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class RunningFragment : BaseFragment() ,IllegalParkingDialogFragment.Callback{


    override val mLayoutRes: Int
        get() = R.layout.fragment_running

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        when (SotsuseiClientAppService.start()) {
            StateResult.ALREADY_RUNNING -> ToastHelper.alreadyRunningService()
            StateResult.SUCCESS_RUN -> ToastHelper.runService()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onResume() {
        super.onResume()
        toolbarColor(R.color.colorPrimaryDark2)
        (activity as HomeActivity).navigationView.setBackgroundColor(ResUtil.color(R.color.bg_main_running))
    }

    override fun setEvent() {

        /**
         * ストップボタン
         */
        stopButton.setOnClickListener {
            when (SotsuseiClientAppService.stop(SotsuseiClientAppService::class.java)) {
                StateResult.SUCCESS_STOP -> ToastHelper.stopService()
                StateResult.ALREADY_STOPPED -> ToastHelper.alreadyStopService()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                popBackStack()
            }, 400)
        }

        illegalparkingButton.setOnClickListener {
            NumberPlateScanActivity.start(activity)
        }

        shutterButton.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NumberPlateScanActivity.REQUEST_CODE) {
            if (resultCode == NumberPlateScanActivity.RESULT_CODE_CANCEL) return
            val p: Plate? = data?.getParcelableExtra<Plate>("data")

            if (p != null) {
//                Toast.makeText(context, plate.number.toString(), Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    IllegalParkingDialogFragment.create {
                        parentFragment = this@RunningFragment
                        plate = p

                    }.show()
                }, 800)

            }
        }
    }

    override fun onPositive() {

    }

    override fun onCancel() {
    }

    /**
     * 戻るボタンの押下イベント
     */
    override fun onBackPressed(): Boolean {
        activity.finish()
        return super.onBackPressed()
    }

}