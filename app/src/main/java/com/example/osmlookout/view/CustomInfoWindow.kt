package com.example.osmlookout.view

import android.view.LayoutInflater
import android.widget.TextView
import com.example.osmlookout.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.InfoWindow
import java.text.SimpleDateFormat

class CustomInfoWindow(
    private val parentMarker: CustomMarker,
    inflater: LayoutInflater,
    map: MapView

) : InfoWindow(
    inflater.inflate(R.layout.marker_info_window, null),
    map
) {
    override fun onOpen(item: Any?) {
        val name = view.findViewById<TextView>(R.id.tw_name)
        name?.let {
            it.text = parentMarker.markerData.name
        }

        val time = view.findViewById<TextView>(R.id.tw_time)
        time?.let {
            val sfd = SimpleDateFormat("HH:mm")
            val strTime: String = sfd.format(parentMarker.markerData.timestamp)
            it.text = parentMarker.context.getString(R.string.info, strTime)
        }

    }

    override fun onClose() {}
}