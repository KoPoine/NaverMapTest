package com.neppplus.navermaptest.Models

import java.io.Serializable

data class GeoResponse(
    var results: ArrayList<ResultData>
): Serializable