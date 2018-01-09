package jp.yuta.kohashi.sotsuseiclientapp.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 01 / 2018
 */
class MyFirebaseInstanceIDService: FirebaseInstanceIdService(){
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        // update token
        val newToken = FirebaseInstanceId.getInstance().token
        // TODO サーバに登録する処理


    }
}