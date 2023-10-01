package com.example.osmlookout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.osmlookout.databinding.ActivityMainBinding
import com.example.osmlookout.util.PermissionManager
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater);
    }

    private val permissionManager : PermissionManager by inject { parametersOf(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!permissionManager.arePermissionsGranted())
            permissionManager.requestPermissions()

        setContentView(binding.root)
    }

}