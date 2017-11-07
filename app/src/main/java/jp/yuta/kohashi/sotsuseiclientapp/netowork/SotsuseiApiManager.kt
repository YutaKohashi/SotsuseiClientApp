package jp.yuta.kohashi.sotsuseiclientapp.netowork

import android.graphics.Bitmap
import io.reactivex.android.schedulers.AndroidSchedulers
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
object SotsuseiApiManager {

    private var disposable: Disposable? = null

    private val mSotsuseiApiService by lazy {
        SotsuseiJsonApiService.create()
    }

    interface Callback<T> {
        fun onSuccess(body: T?)
        fun onFailure(e: ApiException)
    }

    fun postStoreLogin(sId: String, password: String, callback: Callback<Model.Result>) {
        disposable = mSotsuseiApiService.postStoreLogin(sId, password).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ body: Model.Result? ->
                    callback.onSuccess(body)
                }, { error ->
                    callback.onFailure(ApiException.error(error))
                })
    }

    fun uploadImage(bmp: Bitmap, callback: (model: Model.Query?, error: Boolean, type: ApiException.ErrorType) -> Unit) {

        disposable = mSotsuseiApiService.postImage("").
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ result ->
                    val body: Model.Query = result.query
                    callback.invoke(body, false, ApiException.ErrorType.ERROR_TYPE_UNKNOWN)
                }, { error ->
                    error.message
                    callback.invoke(null, true, ApiException.ErrorType.ERROR_TYPE_API_STATUS)
                })
    }

    fun uploadImage(bmp: Bitmap, storeId: String, callback: (model: Model.Query?, error: Boolean, type: ApiException.ErrorType) -> Unit) {

        val imageBody: MultipartBody.Part = RetroUtil.bmp2Part(bmp, "imageData", "fileName")
        val storeIdBody: RequestBody = RetroUtil.string2reqbody(storeId)

        disposable = mSotsuseiApiService.postImage(imageBody, storeIdBody).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe({ result: Model.Result? ->
                    val body = result?.query
                    callback.invoke(body, false, ApiException.ErrorType.ERROR_TYPE_UNKNOWN)
                }, { error ->
                    callback.invoke(null, true, ApiException.ErrorType.ERROR_TYPE_API_STATUS)
                })
    }


    fun dispose() {
        if (disposable != null && !disposable!!.isDisposed) disposable?.dispose()
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
    }

    // endregion

}