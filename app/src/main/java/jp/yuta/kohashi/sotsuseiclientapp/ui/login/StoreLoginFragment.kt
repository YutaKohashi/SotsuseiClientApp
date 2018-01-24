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
import jp.yuta.kohashi.sotsuseiclientapp.utils.NetworkUtil
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import kotlinx.android.synthetic.main.fragment_login_tenpo.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */
class StoreLoginFragment : BaseFragment() {

    override val mLayoutRes: Int
        get() = R.layout.fragment_login_tenpo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setEvent() {
        storeIdEditText.setText("00000")
        storePassEditText.setText("00000")
        //
        val token = PrefUtil.storeToken
        if (!TextUtils.isEmpty(token)) {
            val storeId = PrefUtil.storeId
            SotsuseiApiHelper.postStoreVerifyToken(storeId, token,
                    object : SotsuseiApiHelper.Callback<Model.DefaultResponse> {
                        override fun onSuccess(body: Model.DefaultResponse?) {
                            (activity as LoginActivity).replaceFragment(EmployeeLoginFragment())
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
            val storeId = storeIdEditText.text.toString()
            val storePassword = storePassEditText.text.toString()

            SotsuseiApiHelper.postStoreCreateToken(storeId, storePassword,
                    object : SotsuseiApiHelper.Callback<Model.TokenResponse> {
                        override fun onSuccess(body: Model.TokenResponse?) {
                            if (body != null) {
                                PrefUtil.storeToken = body.token
                                PrefUtil.storeId = storeId
                                PrefUtil.storePass = storePassword
                                (activity as LoginActivity).replaceFragment(EmployeeLoginFragment())
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
    }

    override fun onDestroy() {
        super.onDestroy()
        SotsuseiApiHelper.dispose()
    }

}