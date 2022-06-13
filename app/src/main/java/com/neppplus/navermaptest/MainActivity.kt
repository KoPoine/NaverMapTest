package com.neppplus.navermaptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.map.MapView
import com.neppplus.navermaptest.databinding.ActivityMainBinding
import com.neppplus.navermaptest.naver.APIList
import com.neppplus.navermaptest.naver.ServerAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mPlaceAdapter : PlaceRecyclerViewAdapter
    var mPlaceList = ArrayList<PlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val retrofit = ServerAPI.getRetrofit(this)
        val apiList = retrofit.create(APIList::class.java)

        mPlaceAdapter = PlaceRecyclerViewAdapter(this, mPlaceList)
        binding.recyclerView.adapter = mPlaceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchBtn.setOnClickListener {
            val inputKeyword = binding.keywordEdt.text.toString()
            if (inputKeyword.length < 2) {
                Toast.makeText(this, "검색어는 두글자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val keyword = URLEncoder.encode(inputKeyword, "UTF-8")

            apiList.getRequestSearchKeyword(inputKeyword, 5).enqueue(object : Callback<SearchResponse>{
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    Log.d("json", response.toString())

                    if (response.isSuccessful) {
                        Log.d("성공", response.body().toString())

                        mPlaceList.clear()
                        mPlaceList.addAll(response.body()!!.items)
                        mPlaceAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("서버 실패", t.toString())
                }
            })
        }
    }
}