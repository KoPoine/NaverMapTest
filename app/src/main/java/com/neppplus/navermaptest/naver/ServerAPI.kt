package com.neppplus.navermaptest.naver

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ServerAPI {

    companion object {

        private var retrofit : Retrofit? = null
        private val BASE_URL = "https://api.gudoc.in/"

        fun getRetrofit(): Retrofit {

            val naverClientId = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6ODEsImVtYWlsIjoia21jQHRlc3QuY29tIiwicGFzc3dvcmQiOiI1OTg2YWNjZDJiY2IzOTM0OWUyYTk3OGYxMDMwYzg3ZCJ9.jLDHMVwhtBuGRsizZTKWaiAy67egPYj-jcmrKe8tTIBXJbW0fbm0Y_2tMYstayLEMq2wsHQIDyrWZbIYPGUw0A"

            val interceptor = Interceptor {
                with(it) {
                    val newRequest = request().newBuilder()
                        .addHeader("X-Http-Token", naverClientId)
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