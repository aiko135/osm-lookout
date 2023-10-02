package com.example.osmlookout.view

import android.content.Context
import android.view.MotionEvent
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.DefaultOverlayManager
import org.osmdroid.views.overlay.TilesOverlay

//made for DISABLE DOUBLE TAB gesture
class CustomMapManager(
    mapView: MapView,
    ctx: Context,
    private val onClick: () -> Unit

) : DefaultOverlayManager(
    TilesOverlay(mapView.tileProvider, ctx)
) {
    override fun onTouchEvent(event: MotionEvent?, pMapView: MapView?): Boolean {
        onClick.invoke()
        return super.onTouchEvent(event, pMapView)
    }

    override fun onDoubleTap(e: MotionEvent?, pMapView: MapView?): Boolean {
        return true;
    }

    override fun onDoubleTapEvent(e: MotionEvent?, pMapView: MapView?): Boolean {
        return true
    }
}