package com.example.osmlookout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.osmlookout.databinding.ActivityMainBinding
import com.example.osmlookout.util.PermissionManager
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

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