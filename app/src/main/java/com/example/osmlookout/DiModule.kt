package com.example.osmlookout

import com.example.osmlookout.util.PermissionManager
import com.example.osmlookout.vm.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val diModule = module {

    singleOf(::PermissionManager)

    viewModelOf(::MapViewModel)

}