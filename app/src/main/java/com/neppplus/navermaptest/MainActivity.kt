package com.neppplus.navermaptest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.navermaptest.Models.BasicResponse
import com.neppplus.navermaptest.Models.PlaceData
import com.neppplus.navermaptest.Models.SearchResponse
import com.neppplus.navermaptest.databinding.ActivityMainBinding
import com.neppplus.navermaptest.naver.APIList
import com.neppplus.navermaptest.naver.NaverServerAPI
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
    lateinit var mContext : Context
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mContext = this

        val retrofit = NaverServerAPI.getRetrofit()
        val apiList = retrofit.create(APIList::class.java)

        val gudocRetrofit = ServerAPI.getRetrofit()
        val guApiList = gudocRetrofit.create(APIList::class.java)

        mPlaceAdapter = PlaceRecyclerViewAdapter(this, mPlaceList)
        binding.recyclerView.adapter = mPlaceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.deleteBtn.setOnClickListener {
            permissionChecker()
        }

        binding.searchBtn.setOnClickListener {
            val inputKeyword = binding.keywordEdt.text.toString()
            if (inputKeyword.length < 2) {
                Toast.makeText(this, "???????????? ????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val keyword = URLEncoder.encode(inputKeyword, "UTF-8")

            apiList.getRequestSearchKeyword(inputKeyword, 5).enqueue(object : Callback<SearchResponse>{
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    Log.d("json", response.toString())

                    if (response.isSuccessful) {
                        Log.d("??????", response.body().toString())

                        mPlaceList.clear()
                        mPlaceList.addAll(response.body()!!.items)
                        mPlaceAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("?????? ??????", t.toString())
                }
            })
        }
    }

    fun permissionChecker() {
        val pl: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                getCurrentLocation()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

            }
        }

        TedPermission.create()
            .setPermissionListener(pl)
            .setRationaleMessage("????????? ????????? ?????????\n?????? ?????? ?????? ????????? ???????????????.")
            .setDeniedMessage("????????? ???????????? ????????? ????????? ??????????????????. ?????? > ???????????? ??????????????????.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

        mFusedLocationClient.lastLocation.addOnCompleteListener(this) {task ->
            var location: Location? = task.result
            if (location == null) {
                requestNewLocationData()
            } else {
                latitude = location.latitude
                longitude = location.longitude
                showMapView()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            latitude = mLastLocation.latitude
            longitude = mLastLocation.longitude
            showMapView()
        }
    }

    fun showMapView() {
        binding.latLngTxt.text = "?????? : ${latitude}, ?????? : $longitude"
    }
}