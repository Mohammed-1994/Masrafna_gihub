package com.example.masrafna.api

import android.util.Log
import com.example.masrafna.util.Session
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "MainAPIClient myTag"
object MainAPIClient {
    private const val BASE_URL = "https://api.masrafna-iq.com/api/app/"


    fun getClient(): MainInterface {
        val requestInterceptor = Interceptor {
            val url = it.request()
                .url()
                .newBuilder()
                .build()
            val request = it.request()
                .newBuilder()
                .url(url)
                .addHeader("Accept","application/json")
                .addHeader("Authorization","Bearer "+Session.token)
                .addHeader("Content-Type","application/json")
                .build()
            Log.d(TAG, "getClient: ${Session.token}")
            return@Interceptor it.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MainInterface::class.java)
    }

}