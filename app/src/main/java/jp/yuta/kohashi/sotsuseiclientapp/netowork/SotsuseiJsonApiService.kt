package jp.yuta.kohashi.sotsuseiclientapp.netowork

import android.text.TextUtils
import android.util.Log
import io.reactivex.Observable
import jp.yuta.kohashi.sotsuseiclientapp.netowork.model.Model
import jp.yuta.kohashi.sotsuseiclientapp.utils.PrefUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.InetSocketAddress
import java.net.Proxy


/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 17 / 10 / 2017
 */
interface SotsuseiJsonApiService {

    //　ログイン
    // 店舗ログイン
    @Multipart
    @POST("/api/v1/store/createtoken")
    fun postStoreCreateToken(@Part("sid") sId: RequestBody, @Part("password") password: RequestBody): Observable<Model.TokenResponse>

    @Multipart
    @POST("/api/v1/employee/createtoken")
    fun postEmpCreateToken(@Part("sid ") sId: RequestBody, @Part("eid") eId: RequestBody, @Part("password") password: RequestBody): Observable<Model.TokenResponse>

    @Multipart
    @POST("/api/v1/store/verifytoken")
    fun postStoreVerifyToken(@Part("sid") sId: RequestBody, @Part("token") token: RequestBody): Observable<Model.DefaultResponse>

    @Multipart
    @POST("/api/v1/employee/verifytoken")
    fun postEmpVerifyToken(@Part("sid") sid: RequestBody, @Part("eid") eid: RequestBody, @Part("token") token: RequestBody): Observable<Model.DefaultResponse>

    @Multipart
    @POST("/api/v1/revocationtoken")
    fun postRevocationToken(@Part("token") token: RequestBody): Observable<Model.DefaultResponse>

    // 従業員情報取得
    @GET("/api/v1/employee/info/{eid}/")
    fun getEmpInfo(@Path("eid") eid: String): Observable<Model.Employee>

    // 店舗情報の取得
    @GET("/api/v1/store/info/{sid}/")
    fun getStoreInfo(@Path("sid") storeId: String): Observable<Model.StoreInfo>

    @Multipart
    // ナンバープレート登録
    @POST("/api/v1/numberplate")
    fun postNumberPlate(@Part("eid") eid: RequestBody,@Part("sid") sid: RequestBody, @Part("shiyohonkyochi") shiyohonkyochi: RequestBody, @Part("bunruibango") bunruibango: RequestBody, @Part("jigyoyohanbetsumoji") jigyoyohanbetsumoji: RequestBody, @Part("ichirenshiteibango") ichirenshiteibango: RequestBody, @Part("cartype") cartype: RequestBody, @Part("colortype") colortype: RequestBody, @Part("makertype") makertype: RequestBody, @Part("comment") comment: RequestBody): Observable<Model.DefaultResponse>

    @Multipart
    @POST("/api/v1/shutter")
    fun postShutter(@Part("sid") sid: RequestBody, @Part("eid") eid: RequestBody): Observable<Model.DefaultResponse>

    companion object {

        val PORT = if(PrefUtil.debug && !TextUtils.isEmpty(PrefUtil.debugServerPort)) ":" + PrefUtil.debugServerPort else ""
        private val DOMAIN = if (PrefUtil.debug) PrefUtil.debugServerIp else "59.106.217.144"
        private var BASE_URL = "http://" + DOMAIN + PORT

        fun create(): SotsuseiJsonApiService {

            val interceptor = Interceptor { chain ->
                chain.proceed(chain.request().newBuilder()
                        .header("Accept", "*/*")
//                        .header("Content-Type","application/json")
                        .method(chain.request().method(), chain.request().body())
                        .build())
            }

            val client = OkHttpClient.Builder().apply {
                addInterceptor(interceptor)
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
                if(PrefUtil.debug && PrefUtil.debugProxy){
                    proxy( Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved("proxy.ecc.ac.jp", 8080)))
                    Log.d("SotsuseiJsonApiService","enable proxy")
                }
//                connectTimeout(1000, TimeUnit.MILLISECONDS)
//                readTimeout(1000, TimeUnit.MILLISECONDS)
//                writeTimeout(1000, TimeUnit.MILLISECONDS)
            }.build()

            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
                    .create(SotsuseiJsonApiService::class.java)
        }
    }
}