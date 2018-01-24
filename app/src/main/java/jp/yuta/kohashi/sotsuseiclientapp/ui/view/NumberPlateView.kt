package jp.yuta.kohashi.sotsuseiclientapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import jp.yuta.kohashi.sotsuseiclientapp.R

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 11 / 01 / 2018
 */
class NumberPlateView : RelativeLayout {

    private lateinit var mHonkyochiEditText: EditText
    private lateinit var mBunruiNumEditText: EditText
    private lateinit var mHanbetsuMojiEditText: EditText
    private lateinit var mNumEditText01: EditText
    private lateinit var mNumEditText02: EditText
    private lateinit var mNumEditText03: EditText
    private lateinit var mNumEditText04: EditText

    var honkyochi: String
        get() = mHonkyochiEditText.text.toString()
        set(text) = mHonkyochiEditText.setText(text)

    var bunruiNum: Int
        get() = mBunruiNumEditText.text.toString().toInt()
        set(text) = mBunruiNumEditText.setText(text.toString())

    var hanbetsuMoji: String
        get() = mHanbetsuMojiEditText.text.toString()
        set(text) = mHanbetsuMojiEditText.setText(text)

    var num01: String
        get() = mNumEditText01.text.toString()
        set(num) = mNumEditText01.setText(num)

    var num02: String
        get() = mNumEditText02.text.toString()
        set(num) = mNumEditText02.setText(num)

    var num03: String
        get() = mNumEditText03.text.toString()
        set(num) = mNumEditText03.setText(num)

    var num04: String
        get() = mNumEditText04.text.toString()
        set(num) = mNumEditText04.setText(num)

    var number: Int?
        get() = getnumber()
        set(number) = applyNumber(number)


    /**
     * constructors
     */
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context)
    }

    private fun initView(context: Context) {
        // inflate layout resouce
        val view = View.inflate(context, R.layout.view_number_plate, this)

        mHonkyochiEditText = view.findViewById(R.id.shozai_text_view)
        mBunruiNumEditText = view.findViewById(R.id.bunrui_text_view)
        mHanbetsuMojiEditText = view.findViewById(R.id.hanbetsu_text_view)
        mNumEditText01 = view.findViewById(R.id.number_text_01)
        mNumEditText02 = view.findViewById(R.id.number_text_02)
        mNumEditText03 = view.findViewById(R.id.number_text_03)
        mNumEditText04 = view.findViewById(R.id.number_text_04)

    }

    /**
     * 取得した数値をViewに適用
     */
    fun applyNumber(number: Int?) {
        try {
            val numList = mutableListOf<String>().apply { number.toString().forEach { char -> add(char.toString()) } }
            numList.reverse()
            num04 = numList[0]
            num03 = numList[1]
            num02 = numList[2]
            num01 = numList[3]
        } catch (e: Exception) {

        }
    }


    fun getnumber(): Int? {
        var res = ""
        if (num01.isNumber()) res += num01
        if (num02.isNumber()) res += num02
        if (num03.isNumber()) res += num03
        if (num04.isNumber()) res += num04
        try {
            return res.toInt()
        } catch (e: Exception) {
            return null
        }
    }


    private fun String.isNumber(): Boolean {
        try {
            Integer.parseInt(this)
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }

}