package com.example.osmlookout.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {

    private val _myPos = MutableStateFlow<GeoPoint>(GeoPoint(55.769687, 37.597566))
    val myPos: StateFlow<GeoPoint> = _myPos.asStateFlow()

}