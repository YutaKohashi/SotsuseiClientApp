package jp.yuta.kohashi.sotsuseiclientapp.ui.home

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.running.RunningFragment
import jp.yuta.kohashi.sotsuseiclientapp.ui.view.Circle
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 27 / 09 / 2017
 */

class HomeFragment : BaseFragment() {

    override val mLayoutRes: Int
        get() = R.layout.fragment_home


    override fun setEvent() {
        val animation = Circle.CircleAngleAnimation(circle, 240)
        animation.duration = 1000
        circle.startAnimation(animation)

        startButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
                    !(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted) {
                showNotifyPermissionDialog {
                    startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
                }
                return@setOnClickListener
            }

            Handler(Looper.getMainLooper()).postDelayed({
                (activity as HomeActivity).addFragment(RunningFragment())
            }, 400)

        }

    }

    private fun showNotifyPermissionDialog(callback: () -> Unit) {
        AlertDialog.Builder(context).setTitle("").setMessage("音量キーで操作するためにアクセス許可が必要です。")
                .setPositiveButton("設定画面を開く", { _, _ ->
                    callback.invoke()
                }).create().show()
    }

    override fun onBackPressed(): Boolean {
        activity.finish()
        return super.onBackPressed()
    }
}