package com.neppplus.navermaptest.naver

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NaverServerAPI {

    companion object {

        private var retrofit : Retrofit? = null
        private val BASE_URL = "https://openapi.naver.com/v1/"

        fun getRetrofit(): Retrofit {

            val naverClientId = "Nn1NEDxOmKq1F9QTs0ZG"
            val naverClientSecret = "NGdETq1gha"

            val interceptor = Interceptor {
                with(it) {
                    val newRequest = request().newBuilder()
                        .addHeader("X-Naver-Client-Id", naverClientId)
                        .addHeader("X-Naver-Client-Secret", naverClientSecret)
                        .build()
                    proceed(newRequest)
                }
            }

            val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(myClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }

    }

}