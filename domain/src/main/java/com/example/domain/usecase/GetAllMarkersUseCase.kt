package com.example.domain.usecase

import com.example.domain.irepo.MarkerRepository
import com.example.domain.model.MarkerData

class GetAllMarkersUseCase(
    private val repo:MarkerRepository
) {
    fun proceed():List<MarkerData>{
        return repo.getAllMarkers()
    }
}