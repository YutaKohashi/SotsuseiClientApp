package jp.yuta.kohashi.sotsuseiclientapp.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException

/**
 * Created by yutakohashi on 2017/01/22.
 */

abstract class BasePeriodicService : Service() {

    /**
     * 定期処理を実行する間隔を指定
     *
     * @return
     */
    protected abstract val interval: Long

    protected val binder: IBinder = object : Binder() {
        @Throws(RemoteException::class)
        override fun onTransact(code: Int, data: Parcel, reply: Parcel, flags: Int): Boolean {
            return super.onTransact(code, data, reply, flags)
        }
    }

    /**
     * 定期処理を実行したいタスク
     */
    protected abstract fun  execTask()

    /**
     * 実行計画
     */
    protected abstract fun makeNextPlan()

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    /**
     * 常駐開始
     *
     * @param context
     * @return
     */
    fun startResident(context: Context): BasePeriodicService {
        val intent = Intent(context, this.javaClass)
        intent.putExtra("type", "start")
        context.startService(intent)
        // show toast
        return this
    }
//
//    /**
//     * @param intent
//     * @param startId
//     */
//    override fun onStart(intent: Intent, startId: Int) {
//        super.onStart(intent, startId)
//
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        execTask()
        return Service.START_REDELIVER_INTENT
    }

    /**
     * 次回のサービス予約
     */
    fun scheduleNextTime() {
        val now = System.currentTimeMillis()
        val alarmSender = PendingIntent.getService(this, 0, Intent(this, this.javaClass), 0)
        val am = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC, now + interval, alarmSender)
    }

    /**
     * サービスの定期実行を解除
     *
     * @param context
     */
    fun stopResident(context: Context) {
        // サービス名を指定
        val intent = Intent(context, this.javaClass)

        //アラームを解除
        val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        stopSelf()
    }
}
