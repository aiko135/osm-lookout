package com.example.osmlookout.view

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.osmlookout.databinding.FragmentMapBinding
import com.example.osmlookout.vm.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController


class MapFragment : Fragment() {
    private val defaultCenter = GeoPoint(55.769687, 37.597566) //TODO TO VIEWMODEL
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
        binding.map.controller.setZoom(18.0)
        binding.map.controller.setCenter(defaultCenter)
        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        return binding.root
    }

}