package jp.yuta.kohashi.sotsuseiclientapp.ui.info

import android.app.ProgressDialog
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.netowork.SotsuseiApiHelper
import jp.yuta.kohashi.sotsuseiclientapp.netowork.exception.ApiException
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.service.SotsuseiClientAppService
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.ResUtil
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 15 / 01 / 2018
 */

class InfoTenpoFragment : BaseFragment() {

    override val mLayoutRes: Int
        get() = R.layout.fragment_info

    override fun setEvent() {

        closeButton.setOnClickListener { activity.finish() }

        val prog = ProgressDialog(activity)
        prog.setMessage("通信中...")
        prog.show()
        // 通信
        SotsuseiApiHelper.getStoreInfo(PrefUtil.storeId,
                object : SotsuseiApiHelper.Callback<Model.StoreInfo> {
                    override fun onFailure(e: ApiException) {
                        (activity as InfoTenpoActivity).showSnackBar("error")
                        prog.dismiss()
                    }

                    override fun onSuccess(body: Model.StoreInfo?) {
                        body?.let { applyStoreData(it) } ?: (activity as InfoTenpoActivity).showSnackBar("error")
                        prog.dismiss()
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        if (SotsuseiClientAppService.isRunning()) {
            toolbarColor(R.color.colorPrimaryDark2)
            fragment_info.setBackgroundColor(ResUtil.color(R.color.bg_main_running))
            storeNameTextView.setBackgroundColor(ResUtil.color(R.color.colorPrimaryDark2))
            closeButton.setBackgroundColor(ResUtil.color(R.color.colorPrimaryDark2))
        }
    }

    override fun onPause() {
        super.onPause()
        if (!SotsuseiClientAppService.isRunning()) toolbarColor(R.color.colorPrimaryDark)
    }

    override fun onDestroy() {
        super.onDestroy()
        SotsuseiApiHelper.dispose()
    }

    fun applyStoreData(storeInfo: Model.StoreInfo) {
        storeNameTextView2.text = storeInfo.sname
        nameTextView.text = storeInfo.ownername
        addressTextView.text = storeInfo.address
    }

}