package jp.yuta.kohashi.sotsuseiclientapp.netowork

import android.graphics.Bitmap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.yuta.kohashi.sotsuseiclientapp.netowork.exception.ApiException
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.utils.Util
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 17 / 10 / 2017
 */
object SotsuseiApiHelper {

    //    private var disposable: Disposable? = null
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()


    private val mSotsuseiApiService by lazy {
        SotsuseiJsonApiService.create()
    }

    interface Callback<T> {
        fun onSuccess(body: T?)
        fun onFailure(e: ApiException)
    }


    fun postStoreCreateToken(sid: String, pass: String, callback: Callback<Model.TokenResponse>) {
        val storeIdBody: RequestBody = RetroUtil.string2reqbody(sid)
        val passwordBody: RequestBody = RetroUtil.string2reqbody(pass)

        val disposable: Disposable = mSotsuseiApiService.postStoreCreateToken(storeIdBody, passwordBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.TokenResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    fun postEmpCreateToken(sid: String, eid: String, pass: String, callback: Callback<Model.TokenResponse>) {
        val sidBody: RequestBody = RetroUtil.string2reqbody(sid)
        val eidBody: RequestBody = RetroUtil.string2reqbody(eid)
        val passwordBody: RequestBody = RetroUtil.string2reqbody(pass)

        val disposable: Disposable = mSotsuseiApiService.postEmpCreateToken(sidBody, eidBody, passwordBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.TokenResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    fun postStoreVerifyToken(sid: String, token: String, callback: Callback<Model.DefaultResponse>) {
        val sidBody: RequestBody = RetroUtil.string2reqbody(sid)
        val tokenBody: RequestBody = RetroUtil.string2reqbody(token)

        val disposable: Disposable = mSotsuseiApiService.postStoreVerifyToken(sidBody, tokenBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.DefaultResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    fun postEmpVerifyToken(sid: String, eid: String, token: String, callback: Callback<Model.DefaultResponse>) {
        val sidBody: RequestBody = RetroUtil.string2reqbody(sid)
        val eidBody: RequestBody = RetroUtil.string2reqbody(eid)
        val tokenBody: RequestBody = RetroUtil.string2reqbody(token)

        val disposable: Disposable = mSotsuseiApiService.postEmpVerifyToken(sidBody, eidBody, tokenBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.DefaultResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }


    fun postRevocationToken(token: String, callback: Callback<Model.DefaultResponse>) {
        val tokenBody: RequestBody = RetroUtil.string2reqbody(token)

        val disposable: Disposable = mSotsuseiApiService.postRevocationToken(tokenBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.DefaultResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    /**
     * 従業員情報取得
     */
    fun getEmployeeInfo(eId: String, callback: Callback<Model.Employee>) {
//        val eid = 20001 // TODO
        val disposable: Disposable = mSotsuseiApiService.getEmpInfo(eId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.Employee? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    /**
     * 店舗情報を取得
     */
    fun getStoreInfo(sid: String, callback: Callback<Model.StoreInfo>) {
//        val sid = "20001" // TODO
        val disposable: Disposable = mSotsuseiApiService.getStoreInfo(sid).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.StoreInfo? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    /**
     *　ナンバープレートの登録
     */
    fun postNumberPlate(car: Model.NumberPlate, callback: Callback<Model.DefaultResponse>) {
        val eidBody: RequestBody = RetroUtil.string2reqbody(car.eid)
        val sidBody: RequestBody = RetroUtil.string2reqbody(car.sid)
        val shiyohonkyochiBody: RequestBody = RetroUtil.string2reqbody(car.shiyohonkyochi)
        val bunruibangoBody: RequestBody = RetroUtil.num2reqbody(car.bunruibango)
        val jigyoyohanbetsumojiBody: RequestBody = RetroUtil.string2reqbody(car.jigyoyohanbetsumoji)
        val ichirenshiteibangoBody: RequestBody = RetroUtil.num2reqbody(car.ichirenshiteibango)
        val cartypeBody: RequestBody = RetroUtil.num2reqbody(car.cartype)
        val colortypeBody: RequestBody = RetroUtil.num2reqbody(car.colortype)
        val makertypeBody: RequestBody = RetroUtil.num2reqbody(car.makertype)
        val commentBody: RequestBody = RetroUtil.string2reqbody(car.comment)
        val disposable: Disposable = mSotsuseiApiService.postNumberPlate(eidBody,sidBody, shiyohonkyochiBody, bunruibangoBody, jigyoyohanbetsumojiBody, ichirenshiteibangoBody, cartypeBody, colortypeBody, makertypeBody, commentBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.DefaultResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)

    }

    /**
     * シャッター
     */
    fun postShutter(sid: String, eid: String, callback: Callback<Model.DefaultResponse>) {
        val sidBody: RequestBody = RetroUtil.string2reqbody(sid)
        val eidBody: RequestBody = RetroUtil.string2reqbody(eid)

        val disposable: Disposable = mSotsuseiApiService.postShutter(sidBody, eidBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.DefaultResponse? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }


    fun dispose() {
        mCompositeDisposable.clear()
    }


    // region private methods


    private object RetroUtil {
        fun bmp2Part(bmp: Bitmap, name: String, fileName: String): MultipartBody.Part {
            return reqbody2part(bmp2reqbody(bmp), name, fileName)
        }

        fun reqbody2part(requestBody: RequestBody, name: String, fileName: String): MultipartBody.Part {
            return MultipartBody.Part.createFormData(name, "fileName.png", requestBody)
        }

        fun bmp2reqbody(bitmap: Bitmap): RequestBody {
            return RequestBody.create(MediaType.parse("image/png"), Util.bmp2byte(bitmap))
        }

        fun string2reqbody(string: String): RequestBody {
            return RequestBody.create(MediaType.parse("multipart/form-data"), string)
        }

        fun num2reqbody(num: Int): RequestBody {
            return RequestBody.create(MediaType.parse("multipart/form-data"), num.toString())
        }
    }

    // endregion

}