package com.neppplus.navermaptest.naver

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NaverMapServerAPI {
    companion object {

        private var retrofit : Retrofit? = null
        private val BASE_URL = "https://naveropenapi.apigw.ntruss.com/"

        fun getRetrofit(): Retrofit {

            val naverMapClientId = "8e7qal9zm6"
            val naverMapClientSecret = "kKtHVnK7AJqXDohMS8H0aeTIOPghVr7REXPo3y6C"

            val interceptor = Interceptor {
                with(it) {
                    val newRequest = request().newBuilder()
                        .addHeader("X-NCP-APIGW-API-KEY-ID", naverMapClientId)
                        .addHeader("X-NCP-APIGW-API-KEY", naverMapClientSecret)
                        .build()
                    proceed(newRequest)
                }
            }

            val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }

    }
}