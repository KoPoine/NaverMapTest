package com.neppplus.navermaptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.map.MapView
import com.neppplus.navermaptest.databinding.ActivityMainBinding
import com.neppplus.navermaptest.naver.APIList
import com.neppplus.navermaptest.naver.ServerAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val retrofit = ServerAPI.getRetrofit(this)
        val apiList = retrofit.create(APIList::class.java)

        binding.searchBtn.setOnClickListener {
            val inputKeyword = binding.keywordEdt.text.toString()
            if (inputKeyword.length < 2) {
                Toast.makeText(this, "검색어는 두글자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clientId = "Nn1NEDxOmKq1F9QTs0ZG"
            val clientSecret = "NGdETq1gha"

            apiList.getRequestSearchKeyword(clientId, clientSecret, "local", inputKeyword).enqueue(object : Callback<JSONObject>{
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    Log.d("json", response.toString())
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                }
            })
        }
    }
}