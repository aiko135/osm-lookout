package com.example.osmlookout.vm

import androidx.lifecycle.ViewModel
import com.example.domain.model.MarkerData
import com.example.domain.usecase.GetAllMarkersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.util.GeoPoint

class MapViewModel(
    getAllMarkersUseCase: GetAllMarkersUseCase
) : ViewModel() {

    data class UiState(
        val myPosition: GeoPoint,
        val markerData: List<MarkerData>
    )

    private val _uiState = MutableStateFlow<UiState>(UiState(
        GeoPoint(0.0,0.0),
        getAllMarkersUseCase.proceed()
    ))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateMyPos(lat:Double, lng:Double){
        _uiState.update {prev ->
            UiState(GeoPoint(lat, lng), prev.markerData)
        }
    }

}