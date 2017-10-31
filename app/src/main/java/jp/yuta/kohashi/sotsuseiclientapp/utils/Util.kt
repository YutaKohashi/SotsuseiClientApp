package jp.yuta.kohashi.sotsuseiclientapp.utils

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 10 / 10 / 2017
 */

object Util {


    /**
     * ブロードキャストレシーバが登録されているか
     * 引数:Intentクラスの定数を使用
     */
    fun registeredBroadcastReceiver(receiver: String): Boolean {
        return false
    }

    fun layoutRes2View(context: Context, @LayoutRes layoutRes: Int, root: ViewGroup, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, root, attachToRoot)
    }


}