package jp.yuta.kohashi.sotsuseiclientapp.ui.running

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.netowork.FirebaseHelper
import jp.yuta.kohashi.sotsuseiclientapp.netowork.SotsuseiApiHelper
import jp.yuta.kohashi.sotsuseiclientapp.netowork.exception.ApiException
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.service.StateResult
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.home.HomeActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.IllegalParkingDialogFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.NumberPlateScanActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking.Plate
import jp.yuta.kohashi.sotsuseiclientapp.utils.NetworkUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil
import kotlinx.android.synthetic.main.fragment_running.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class RunningFragment : BaseFragment(), IllegalParkingDialogFragment.Callback {
    private val TAG = RunningFragment::class.java.simpleName

    override val mLayoutRes: Int
        get() = R.layout.fragment_running

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        when (SotsuseiClientAppService.start()) {
            StateResult.ALREADY_RUNNING -> (activity as HomeActivity).showSnackBar(ResUtil.string(R.string.already_running_servce), 100)
            StateResult.SUCCESS_RUN -> (activity as HomeActivity).showSnackBar(ResUtil.string(R.string.success_runnning_service), 400)
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
            when (SotsuseiClientAppService.stop()) {
                StateResult.SUCCESS_STOP -> (activity as HomeActivity).showSnackBar(ResUtil.string(R.string.success_stop_servivce), 700)
                StateResult.ALREADY_STOPPED -> (activity as HomeActivity).showSnackBar(ResUtil.string(R.string.already_stop_service))
            }

            Handler(Looper.getMainLooper()).postDelayed({
                // 店舗IDでトピックに登録解除
//                val storeId = "sample"
                val storeId = PrefUtil.storeId
                FirebaseHelper.unsubscribeFromTopic(storeId)

                popBackStack()
            }, 400)
        }

        illegalparkingButton.setOnClickListener {
            NumberPlateScanActivity.start(activity)
        }

        // シャッタボタン
        shutterButton.setOnClickListener {
            //            IllegalParkingDialogFragment.create {
//                parentFragment = this@RunningFragment
//                plate = Plate().apply { number = 2342 }
//
//            }.show()
            if (!NetworkUtil.isConnectNetwork()) {
                (activity as HomeActivity).showSnackBar("ネットワークに接続されていません")
                return@setOnClickListener
            }
            val prog = ProgressDialog(activity)
            prog.setMessage("通信中...")
            prog.show()
            SotsuseiApiHelper.postShutter(PrefUtil.storeId, PrefUtil.empId,
                    object : SotsuseiApiHelper.Callback<Model.DefaultResponse> {
                        override fun onFailure(e: ApiException) {
                            (activity as HomeActivity).showSnackBar("error")
                            prog.dismiss()
                        }

                        override fun onSuccess(body: Model.DefaultResponse?) {
                            if (body != null) {
//                                applyEmpData(body)
                                (activity as HomeActivity).showSnackBar("シャッターが押されました")
                            } else {
                                (activity as HomeActivity).showSnackBar("error")
                            }
                            prog.dismiss()
                        }
                    })
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

    override fun onPositive(numberPlate: Model.NumberPlate) {
        Log.d(TAG, "onPositive")
        if (!NetworkUtil.isConnectNetwork()) {
            (activity as HomeActivity).showSnackBar("ネットワークに接続されていません")
            return
        }

        val prog = ProgressDialog(context!!)
        prog.setMessage("通信中...")
        prog.setCancelable(false)
        prog.show()

        SotsuseiApiHelper.postNumberPlate(numberPlate,
                object : SotsuseiApiHelper.Callback<Model.DefaultResponse> {
                    override fun onSuccess(body: Model.DefaultResponse?) {
                        if(body != null){
                            (activity as HomeActivity).showSnackBar("登録しました",700)
                        } else {
                            (activity as HomeActivity).showSnackBar("error")
                        }
                        prog.dismiss()
                    }

                    override fun onFailure(e: ApiException) {
                        (activity as HomeActivity).showSnackBar("error")
                        prog.dismiss()
                    }
                })
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

    override fun onDestroy() {
        super.onDestroy()
        SotsuseiApiHelper.dispose()
    }
}