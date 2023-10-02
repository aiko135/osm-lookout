package com.example.domain.irepo

import com.example.domain.model.MarkerData

interface MarkerRepository {
    fun getAllMarkers():List<MarkerData>
}