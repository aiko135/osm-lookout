package com.example.osmlookout.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.MarkerData
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
import java.text.SimpleDateFormat


class MapFragment : Fragment() {
    private val viewModel: MapViewModel by viewModel()
    private val binding by lazy {
        FragmentMapBinding.inflate(layoutInflater);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ctx = activity?.applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        closeBottomSheet()

        binding.map.run {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            overlayManager = CustomMapManager(binding.map, requireActivity()){
                //on map click
                closeBottomSheet()
            }
            //Default values
            controller.setZoom(15.0)
            controller.setCenter(GeoPoint(55.769687, 37.597566))
        }

        //Buttons
        binding.plus.setOnClickListener{
            binding.map.controller.setZoom(binding.map.zoomLevelDouble * 1.05)
        }
        binding.minus.setOnClickListener{
            binding.map.controller.setZoom(binding.map.zoomLevelDouble * 0.95)
        }
        binding.mylocation.setOnClickListener{
            getLocation()
        }

        //observe the UI state
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                //remove prev markers
                binding.map.overlays.forEach {
                    binding.map.overlays.remove(it);
                }
                binding.map.invalidate();

                //My position marker - add common marker
                val marker = Marker(binding.map)
                marker.position = state.myPosition
                marker.icon = ContextCompat.getDrawable(requireActivity(),com.example.osmlookout.R.mipmap.ic_my_tracker_46dp)
                marker.infoWindow = null
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                binding.map.overlays.add(marker)

                //Peoples markers - add custom markers
                state.markerData.forEach{ marker ->
                    addCustomMarker(marker)
                }
            }
        }


        return binding.root
    }


    private fun addCustomMarker(markerData: MarkerData) {
        val marker = CustomMarker(markerData, binding.map, layoutInflater)
        marker.setOnMarkerClickListener { marker, mapView ->
            //remove all previous infoWindows
            binding.map.overlays.forEach { overlay ->
               if(overlay is Marker)
                   overlay.infoWindow?.let { it.close() }
            }

            marker.showInfoWindow()
            openBottomSheet(markerData)
            return@setOnMarkerClickListener true
        }
        binding.map.overlays.add(marker)
    }

    private fun openBottomSheet(markerData: MarkerData){
        BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.bottomSheet.run {
            textViewName.text = markerData.name

            val sfdDate = SimpleDateFormat("yyyy-MM-dd")
            val dateStr: String = sfdDate.format(markerData.timestamp)
            date.text = dateStr

            val sfdTime = SimpleDateFormat("HH:mm")
            val strTime: String = sfdTime.format(markerData.timestamp)
            time.text = strTime
        }
    }

    private fun closeBottomSheet(){
        BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            peekHeight = 0 //height in closed state is 0
            isHideable = true
            isDraggable = false
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    //request my geolocation
    @SuppressLint("MissingPermission")
    private fun getLocation(){
        try {
            val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val hasGps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(hasGps){
                val gpsLocationListener: LocationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        viewModel.updateMyPos(location.latitude,location.longitude)
                        binding.map.controller.setCenter(GeoPoint(location.latitude,location.longitude))
                        lm.removeUpdates(this)
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    500,
                    1F,
                    gpsLocationListener
                )
            }
            else{
                throw NoSuchElementException()
            }
        }
        catch (e: Exception){
            Toast.makeText(requireActivity(), "Cannot get your location", Toast.LENGTH_LONG).show()
        }

    }

}