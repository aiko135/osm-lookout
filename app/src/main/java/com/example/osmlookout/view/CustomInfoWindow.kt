package com.example.osmlookout.view

import android.view.LayoutInflater
import android.widget.TextView
import com.example.osmlookout.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(
    private val parentMarker: CustomMarker,
    inflater: LayoutInflater,
    map: MapView
) : InfoWindow(
    inflater.inflate(R.layout.marker_info_window, null),
    map
) {
    override fun onOpen(item: Any?) {
        val view = view.findViewById<TextView>(R.id.tw_name)
        view?.let {
            it.text = parentMarker.markerData.name
        }
    }

    override fun onClose() {}
}