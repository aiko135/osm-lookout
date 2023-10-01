package com.example.osmlookout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.osmlookout.databinding.FragmentMainBinding
import org.osmdroid.tileprovider.tilesource.TileSourceFactory


class MainFragment : Fragment() {

    private val binding by lazy{
        FragmentMainBinding.inflate(layoutInflater);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        Log.d("test", "created")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}