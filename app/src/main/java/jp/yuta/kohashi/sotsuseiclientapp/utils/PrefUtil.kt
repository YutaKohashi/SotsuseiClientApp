package jp.yuta.kohashi.sotsuseiclientapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

object PrefUtil {
    private lateinit var prefs: SharedPreferences

    fun setUp(context: Context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    var empId: String
        get() = prefs.getString(KEY_USER_ID,"")
        set(value) { prefs.edit().putString(KEY_USER_ID, value).apply() }

    var empPass:String
        get() = prefs.getString(KEY_USER_PASS, "")
        set(value) {prefs.edit().putString(KEY_USER_PASS, value).apply()}

    var storeId:String
        get() = prefs.getString(KEY_TENPO_ID,"")
        set(value){prefs.edit().putString(KEY_TENPO_ID,value).apply()}

    var storePass:String
        get() = prefs.getString(KEY_TENPO_PASS,"")
        set(value){prefs.edit().putString(KEY_TENPO_PASS,value).apply()}

    var debug:Boolean
        get() = prefs.getBoolean(KEY_DEBUG_IS,false)
        set(value) {prefs.edit().putBoolean(KEY_DEBUG_IS,value).apply()}

    var debugServerIp:String
        get() = prefs.getString(KEY_DEBUG_SERVER_IP, "127.0.0.1")
        set(value)  {prefs.edit().putString(KEY_DEBUG_SERVER_IP,value).apply()}

    var debugServerPort:String
        get() = prefs.getString(KEY_DEBUG_SERVER_PORT, "")
        set(value)  {prefs.edit().putString(KEY_DEBUG_SERVER_PORT,value).apply()}

    var debugProxy:Boolean
        get() = prefs.getBoolean(KEY_DEBUG_PROXY,false)
        set(value) {prefs.edit().putBoolean(KEY_DEBUG_PROXY,value).apply()}

    var storeToken:String
        get() = prefs.getString(KEY_STORE_TOKEN, "")
        set(value)  {prefs.edit().putString(KEY_STORE_TOKEN,value).apply()}

    var empToken:String
        get() = prefs.getString(KEY_EMP_TOKEN, "")
        set(value)  {prefs.edit().putString(KEY_EMP_TOKEN,value).apply()}

    private val KEY_USER_ID = "user_id"
    private val KEY_USER_PASS = "user_pass"
    private val KEY_TENPO_ID = "tenpo_id"
    private val KEY_TENPO_PASS = "tenpo_pass"
    private val KEY_IS_RUNNING = "is_running"
    private val KEY_STORE_TOKEN = "store_token"
    private val KEY_EMP_TOKEN = "emp_token"

    private val KEY_DEBUG_IS = "debug_is"
    private val KEY_DEBUG_SERVER_IP = "debug_server_ip"
    private val KEY_DEBUG_SERVER_PORT = "debug_server_port"
    private val KEY_DEBUG_PROXY = "debug_proxy"
}