package com.neppplus.navermaptest.naver

import com.neppplus.navermaptest.SearchResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIList {

    @GET("local")
    fun getRequestSearchKeyword(@Query("query") keyword : String, @Query("display") display : Int) : Call<SearchResponse>

}