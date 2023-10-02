package com.example.domain.model

import java.util.Date

data class MarkerData(
    val name:String,
    val timestamp:Date,
    val geoLat:Double,
    val geoLng:Double
){
    companion object {
        fun default(): MarkerData {
            return MarkerData("Test", Date(),55.668379, 37.463974)
        }
    }
}