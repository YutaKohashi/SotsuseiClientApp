package jp.yuta.kohashi.sotsuseiclientapp.ui.illegalparking

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 25 / 11 / 2017
 */
class Plate() : Parcelable {
    var number: Int = 0

    var height: Int = 0

    var width: Int = 0

    var x: Int = 0

    var y: Int = 0

    var bmp: Bitmap? = null


    constructor(parcel: Parcel) : this() {
        number = parcel.readInt()
        height = parcel.readInt()
        width = parcel.readInt()
        x = parcel.readInt()
        y = parcel.readInt()
        bmp = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

    constructor(number: Int, height: Int, width: Int, x: Int, y: Int, bmp: Bitmap): this(){
        this.number = number
        this.height = height
        this.width = width
        this.x = x
        this.y = y
        this.bmp = bmp
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeInt(height)
        parcel.writeInt(width)
        parcel.writeInt(x)
        parcel.writeInt(y)
        parcel.writeParcelable(bmp, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Plate> {
        override fun createFromParcel(parcel: Parcel): Plate {
            return Plate(parcel)
        }

        override fun newArray(size: Int): Array<Plate?> {
            return arrayOfNulls(size)
        }
    }

}