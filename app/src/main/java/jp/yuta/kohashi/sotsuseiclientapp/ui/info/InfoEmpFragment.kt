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
import kotlinx.android.synthetic.main.fragment_info_emp.*
import java.text.SimpleDateFormat

/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 15 / 01 / 2018
 */

class InfoEmpFragment :BaseFragment(){

    override val mLayoutRes: Int
        get() = R.layout.fragment_info_emp

    override fun setEvent() {

        closeButton.setOnClickListener { activity.finish() }

        val prog = ProgressDialog(activity)
        prog.setMessage("通信中...")
        prog.show()
        // 通信
        SotsuseiApiHelper.getEmployeeInfo(PrefUtil.empId,
                object : SotsuseiApiHelper.Callback<Model.Employee>{
                    override fun onFailure(e: ApiException) {
                        (activity as InfoEmpActivity).showSnackBar("error")
                        prog.dismiss()
                    }
                    override fun onSuccess(body: Model.Employee?) {
                        if(body != null){
                            applyEmpData(body)
                        } else {
                            (activity as InfoEmpActivity).showSnackBar("error")
                        }
                        prog.dismiss()
                    }
                })

//            res: Model.StoreInfo?, type: ApiException.ErrorType ->
//            if(type != ApiException.ErrorType.ERROR_NO || res == null) {
//                Toast.makeText(activity,"店舗情報の取得に失敗しました。",Toast.LENGTH_SHORT).show()
//                return@getStoreInfo
//            }
//
//            storeNameTextView.text = res.sname
//            addressTextView.text = res.longitude.toString()
//            nameTextView.text = res.ownername
//        })
    }

    override fun onResume() {
        super.onResume()
        if(SotsuseiClientAppService.isRunning()){
            toolbarColor(R.color.colorPrimaryDark2)
            fragment_info.setBackgroundColor(ResUtil.color(R.color.bg_main_running))
            storeNameTextView.setBackgroundColor(ResUtil.color(R.color.colorPrimaryDark2))
            closeButton.setBackgroundColor(ResUtil.color(R.color.colorPrimaryDark2))
        }
    }

    override fun onPause() {
        super.onPause()
        if(!SotsuseiClientAppService.isRunning()) toolbarColor(R.color.colorPrimaryDark)
    }

    override fun onDestroy() {
        super.onDestroy()
        SotsuseiApiHelper.dispose()
    }

    fun applyEmpData(empInfo: Model.Employee) {
        nameTextView.text = empInfo.employeename
        val df = SimpleDateFormat("yyyy年MM月dd日")
        dateTextView.text = df.format(empInfo.date)
    }

}