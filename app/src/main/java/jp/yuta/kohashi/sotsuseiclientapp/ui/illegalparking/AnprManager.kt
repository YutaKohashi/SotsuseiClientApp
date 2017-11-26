package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import jp.yuta.kohashi.sotsuseiclientapp.App
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 25 / 11 / 2017
 */
class AnprManager {

    companion object {

        init {
            System.loadLibrary("native-lib")
            System.loadLibrary("ANPR")
        }

        fun init() {
            aseets2local("OCR.xml")
            aseets2local("SVM.xml")
            aseets2local("testimg.jpg")
        }

        private fun aseets2local(filename: String) {
            try {
                App.context.deleteFile(filename)

                val inputStream = App.context.assets.open(filename)
                val fileOutputStream = App.context.openFileOutput(filename, Context.MODE_PRIVATE)
                val buffer = ByteArray(1024)
                var length = inputStream.read(buffer)
                while (length >= 0) {
                    fileOutputStream.write(buffer, 0, length)
                    length = inputStream.read(buffer)
                }
                fileOutputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     *
     */
    fun apply(): Plate? {
        return convertPlate(stringFromTest())
    }

    fun apply(bitmap: Bitmap): Plate? {
        saveJpg(bitmap)
        return convertPlate(stringFromTest())
    }

    fun apply(bitmap:Bitmap, callback:(Plate?)-> Unit){
        saveJpg(bitmap)
        bitmap.recycle()
        convertPlate(stringFromTest()).apply { callback(this) }
    }

    private fun convertPlate(result: String): Plate? {
        if (result == "-1") {
            return null
        } else {
            try {
                val columns = result.split(",")
                val number = columns[0].toInt()
                val height = columns[1].toInt()
                val width = columns[2].toInt()
                val x = columns[3].toInt()
                val y = columns[4].toInt()

                val bmp = fileInputStream2Bitmap(App.context.openFileInput("testimg.jpg"))
                val trimed = Bitmap.createBitmap(bmp, x, y, width, height, null, true)
                return Plate(number, height, width, x, y, trimed)
            } catch (e: Exception) {
                return null
            }
        }
    }

    private fun saveJpg(bmp: Bitmap) {
        var out: FileOutputStream? = null
        try {
            App.context.deleteFile("testimg.jpg")
            // openFileOutputはContextのメソッドなのでActivity内ならばthisでOK
            out = App.context.openFileOutput("testimg.jpg", Context.MODE_PRIVATE)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: FileNotFoundException) {
            // エラー処理
        } finally {
            if (out != null) {
                out!!.close()
                out = null
            }
        }
    }

    private fun fileInputStream2Bitmap(f: FileInputStream): Bitmap {
        return BitmapFactory.decodeStream(f)
    }

    external fun stringFromTest(): String
}