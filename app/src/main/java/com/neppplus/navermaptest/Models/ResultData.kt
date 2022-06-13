package com.neppplus.navermaptest.Models

import com.nepplus.alleymarket_seller_android.models.LandData
import com.nepplus.alleymarket_seller_android.models.RegionData
import java.io.Serializable

data class ResultData(
    var name: String,
    var region : RegionData,
    var land : LandData
):Serializable