package jp.yuta.kohashi.sotsuseiclientapp.netowork

import com.google.firebase.messaging.FirebaseMessaging

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 09 / 01 / 2018
 */
object FirebaseHelper{

    /**
     * トピック登録
     */
    fun subscribeToTopic(topicname:String){
        FirebaseMessaging.getInstance().subscribeToTopic(topicname)
    }

    /**
     * トピック登録解除
     * */
    fun unsubscribeFromTopic(topicname:String){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicname)
    }
}