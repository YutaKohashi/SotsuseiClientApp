package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.FrameLayout
import jp.yuta.kohashi.sotsuseiclientapp.R
import jp.yuta.kohashi.sotsuseiclientapp.ui.BaseActivity
import jp.yuta.kohashi.sotsuseiclientapp.ui.RegularlyScheduler
import jp.yuta.kohashi.sotsuseiclientapp.ui.StartActivityForResult
import jp.yuta.kohashi.sotsuseiclientapp.ui.view.CameraView
import kotlinx.android.synthetic.main.activity_number_plate_scan.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 25 / 11 / 2017
 */
class NumberPlateScanActivity : BaseActivity() {

    override val contentViewFromRes: Int?
        get() = R.layout.activity_number_plate_scan

    private lateinit var mCameraView: CameraView
    private var mRegularlyScheduler: RegularlyScheduler? = null
    private lateinit var mAnprController: AnprController

    companion object : StartActivityForResult<NumberPlateScanActivity> {
        override val REQUEST_CODE: Int = 2001
        val RESULT_CODE_CANCEL: Int = 2002
        val RESULT_CODE_OK: Int = 2003

        override fun start(activity: Activity) = super.start(activity, NumberPlateScanActivity::class.java)

    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState, false)
        mCameraView = CameraView(this)
        mCameraView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        cameraViewContainer.addView(mCameraView)
        mAnprController = AnprController()
        setEvent()
    }

    override fun onResume() {
        super.onResume()
        mCameraView.onResume()
        mRegularlyScheduler?.onResume()
        toolbarColor(android.R.color.black)
    }

    override fun onPause() {
        mCameraView.onPause()
        mRegularlyScheduler?.onPause()
        super.onPause()
        toolbarColorDefault()
    }

    override fun setEvent() {
        regularlyEvent()

        backButton.setOnClickListener {
            setResult(RESULT_CODE_CANCEL)
            finish()
        }
    }

    fun successScan(plate: Plate) {
        Log.d("NumberPlateScanActivity","successScan")
        mRegularlyScheduler?.onPause()
        val intent = Intent()
        intent.putExtra("data", plate)
        setResult(RESULT_CODE_OK, intent)
        finish()
    }

    /**
     * 定期的なスキャン処理
     */
    private fun regularlyEvent() {

        /**
         * キャプチャコールバック
         */
        val callback: (Bitmap) -> Unit = { bitmap ->
            mAnprController.addBitmap(bitmap)
            Log.d("NumberPlateScanActivity","getPreviewNonNullBitmap")
        }

        mRegularlyScheduler = RegularlyScheduler.Builder {
            periodTime = 333L
            initialDelayTime = 1500L
            job = { mCameraView.getPreviewNonNullBitmap(callback) }
        }.build().start()
    }


    inner class AnprController() {
        private val  anprManager:AnprManager
        init {
            anprManager = AnprManager()
        }

        private var callback: (Plate?) -> Unit = { plate ->
            Log.d("AnprController","callback")
            plate?.let {
                successScan(plate)
            }
        }

        fun addBitmap(bmp: Bitmap) {
            Thread(Runnable {
                Log.d("AnprController","addBitmap")
                val d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                anprManager.apply(bmp, callback)
            }).start()
        }

    }

}