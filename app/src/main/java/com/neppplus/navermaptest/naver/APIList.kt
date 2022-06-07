package com.neppplus.navermaptest.naver

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface APIList {

    @GET("search/{type}")
    fun getRequestSearchKeyword(@Header("X-Naver-Client-Id") id : String, @Header("X-Naver-Client-Secret") pw : String,@Path("type") type: String, @Query("query") keyword : String) : Call<JSONObject>

}