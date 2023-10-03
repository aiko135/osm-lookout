package com.example.data.repository

import com.example.domain.irepo.MarkerRepository
import com.example.domain.model.MarkerData
import java.text.SimpleDateFormat

class MarkerRepositoryImpl:MarkerRepository {
    override fun getAllMarkers(): List<MarkerData> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return listOf(
            MarkerData("Андрей", sdf.parse("2023-09-24 16:50"), 55.789944, 37.560572 ),
            MarkerData("Илья", sdf.parse("2023-10-01 19:33"), 55.761737, 37.575885),
            MarkerData("Дима", sdf.parse("2023-09-25 10:02"), 55.792850, 37.628080),
            MarkerData("Виктор", sdf.parse("2023-10-02 14:00"), 55.795027, 37.675822)
        )
    }
}