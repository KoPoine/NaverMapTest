package com.neppplus.navermaptest.naver

import com.neppplus.navermaptest.Models.GeoResponse
import com.neppplus.navermaptest.Models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIList {

    @GET("search/local")
    fun getRequestSearchKeyword(
        @Query("query") keyword : String,
        @Query("display") display : Int
    ) : Call<SearchResponse>

    @GET("map-reversegeocode/v2/gc")
    fun getRequestMapAddress(
        @Query("coords") coords : String,
        @Query("output") output : String,
        @Query("orders") orders : String
    ) : Call<GeoResponse>
}