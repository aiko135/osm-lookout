package com.example.data.repository

import com.example.domain.irepo.MarkerRepository
import com.example.domain.model.MarkerData
import java.text.SimpleDateFormat

class MarkerRepositoryImpl:MarkerRepository {
    override fun getAllMarkers(): List<MarkerData> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return listOf(
            MarkerData("Андрей", sdf.parse("2023-10-02 16:50"), 55.789944, 37.560572 ),
            MarkerData("Илья", sdf.parse("2023-10-02 18:50"), 55.761737, 37.575885)
        )
    }
}