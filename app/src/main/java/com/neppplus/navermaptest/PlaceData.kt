package com.neppplus.navermaptest

import java.io.Serializable

data class PlaceData(
    val title : String,
    val roadAddress : String,
    val mapx : Int,
    val mapy : Int
) : Serializable {
}