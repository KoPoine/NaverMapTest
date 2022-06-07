package com.neppplus.navermaptest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.neppplus.navermaptest.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    lateinit var binding : ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_map)
        binding.mapView.onCreate(savedInstanceState)
    }
}