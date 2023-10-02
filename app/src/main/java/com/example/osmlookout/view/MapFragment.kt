package com.example.osmlookout.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.osmlookout.databinding.FragmentMapBinding
import com.example.osmlookout.vm.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow


class MapFragment : Fragment() {
    private val viewModel: MapViewModel by viewModel()
    private val binding by lazy {
        FragmentMapBinding.inflate(layoutInflater);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Bottom menu -> not draggable bottom sheet
        BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.standardBottomSheet.isVisible = false

        activity?.applicationContext.let {
            Configuration.getInstance().load(it, PreferenceManager.getDefaultSharedPreferences(it))
        }

        binding.map.run {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            overlayManager = CustomOverlayManager(binding.map, requireActivity())

            controller.setZoom(18.0)
            controller.setCenter(GeoPoint(55.769687, 37.597566))
        }

        lifecycleScope.launch {
            viewModel.myPos.collect {
                addMarker(it)
            }
        }

        return binding.root
    }

    //                marker.icon = ContextCompat.getDrawable(requireActivity(),com.example.osmlookout.R.mipmap.ic_my_tracker_46dp)

    private fun addMarker(point: GeoPoint) {
        val marker = CustomMarker(binding.map, requireActivity())
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.infoWindow = object : InfoWindow(
            layoutInflater.inflate(com.example.osmlookout.R.layout.marker_info_window, null),
            binding.map
        ) {
            override fun onOpen(item: Any?) {}
            override fun onClose() {}
        }
        marker.setOnMarkerClickListener { marker, mapView ->
            marker.showInfoWindow()
            onClickMarker(marker)
            return@setOnMarkerClickListener true
        }
        binding.map.overlays.add(marker)
    }

    private fun onClickMarker(marker: Marker) {
        binding.standardBottomSheet.isVisible = true
    }


}