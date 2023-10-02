package com.example.osmlookout.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.osmlookout.databinding.FragmentMapBinding
import com.example.osmlookout.vm.MapViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {
    private val viewModel: MapViewModel by viewModel()
    private val binding by lazy{
        FragmentMapBinding.inflate(layoutInflater);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ctx = activity?.applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.setMultiTouchControls(true)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        binding.map.overlayManager = CustomOverlayManager(binding.map, requireActivity())

        binding.map.controller.setZoom(18.0)
        binding.map.controller.setCenter(GeoPoint(55.769687, 37.597566))

        lifecycleScope.launch {
            viewModel.myPos.collect{
                val marker = Marker(binding.map)
                marker.position = it
//                marker.icon = ContextCompat.getDrawable(requireActivity(),com.example.osmlookout.R.mipmap.ic_my_tracker_46dp)
                marker.icon = createCustomMarker(requireActivity())
                binding.map.overlays.add(marker)
            }
        }

        return binding.root
    }

}