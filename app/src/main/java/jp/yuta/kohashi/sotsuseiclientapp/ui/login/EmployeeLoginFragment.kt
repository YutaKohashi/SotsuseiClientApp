package jp.yuta.kohashi.sotsuseiclientapp.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.netowork.SotsuseiApiHelper
import jp.yuta.kohashi.sotsuseiclientapp.netowork.exception.ApiException
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.debug.DebugActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.debug.DebugClass
import jp.yuta.kohashi.sotsuseiclientapp.ui.home.HomeActivity
import jp.yuta.kohashi.sotsuseiclientapp.utils.NetworkUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import kotlinx.android.synthetic.main.fragment_login_emp.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */
class EmployeeLoginFragment : BaseFragment() {

    override val mLayoutRes: Int
        get() = R.layout.fragment_login_emp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setEvent() {
        bottomTextView.setText("店舗アカウント "+ PrefUtil.storeId + " でログインしてます。")


        empIdEditText.setText("11111")
        empPassEditText.setText("11111")
        val token = PrefUtil.empToken
        if (!TextUtils.isEmpty(token)) {
            val eid = PrefUtil.empId
            val sid = PrefUtil.storeId
            SotsuseiApiHelper.postEmpVerifyToken(sid, eid, token,
                    object : SotsuseiApiHelper.Callback<Model.DefaultResponse> {
                        override fun onSuccess(body: Model.DefaultResponse?) {
                            activityStart<HomeActivity>(Bundle().apply { putBoolean("successLogin", true) })
                            activity.finishAffinity()
                        }

                        override fun onFailure(e: ApiException) {
                            (activity as LoginActivity).showSnackBar("tokenが無効です")
                        }
                    })
        }

        loginButton.setOnClickListener {
            if(!NetworkUtil.isConnectNetwork()) {
                (activity as LoginActivity).showSnackBar("ネットワークに接続されていません")
                return@setOnClickListener
            }
            val prog = ProgressDialog(context!!)
            prog.setMessage("ログイン中...")
            prog.setCancelable(false)
            prog.show()
            val sid = PrefUtil.storeId
            val eid = empIdEditText.text.toString()
            val ePassword = empPassEditText.text.toString()

            SotsuseiApiHelper.postEmpCreateToken(sid, eid, ePassword,
                    object : SotsuseiApiHelper.Callback<Model.TokenResponse> {
                        override fun onSuccess(body: Model.TokenResponse?) {
                            if (body != null) {
                                PrefUtil.empToken = body.token
                                PrefUtil.empId = eid
                                PrefUtil.empPass = ePassword

                                activity.finish()
                                activity.finishAffinity()
                                activityStart<HomeActivity>(Bundle().apply { putBoolean("successLogin", true) })
                            } else {
                                (activity as LoginActivity).showSnackBar("error")
                            }

                            prog.dismiss()
                        }

                        override fun onFailure(e: ApiException) {
                            (activity as LoginActivity).showSnackBar("error")
                            prog.dismiss()
                        }
                    })
        }

        loginButton.setOnTouchListener(DebugClass(context,{
            activityStart<DebugActivity>()
        }))

        logoutButton.setOnClickListener{
            if(!NetworkUtil.isConnectNetwork()) {
                PrefUtil.storeToken = ""
                activityStart<LoginActivity>(Bundle().apply { putBoolean("successLogout",true) })
                activity.finishAffinity()
                return@setOnClickListener
            }
            val prog = ProgressDialog(activity)
            prog.setMessage("ログアウト中...")
            prog.setCancelable(false)
            prog.show()

            val token = PrefUtil.storeToken
            SotsuseiApiHelper.postRevocationToken(token,
                    object : SotsuseiApiHelper.Callback<Model.DefaultResponse> {
                        override fun onSuccess(body: Model.DefaultResponse?) {
                            if (body != null) {

                            } else {
                                (activity as LoginActivity).showSnackBar("error")
                            }
                            prog.dismiss()
                            PrefUtil.storeToken = ""
                            activityStart<LoginActivity>(Bundle().apply { putBoolean("successLogout",true) })
                            activity.finishAffinity()
                        }

                        override fun onFailure(e: ApiException) {
                            (activity as LoginActivity).showSnackBar("error")
                            prog.dismiss()
                            PrefUtil.storeToken = ""
                            activityStart<LoginActivity>(Bundle().apply { putBoolean("successLogout",true) })
                            activity.finishAffinity()
                        }
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SotsuseiApiHelper.dispose()
    }

}