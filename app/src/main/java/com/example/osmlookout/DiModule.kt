package com.example.osmlookout

import com.example.osmlookout.util.PermissionManager
import com.example.osmlookout.vm.MapViewModel
import  com.example.data.repository.MarkerRepositoryImpl
import com.example.domain.usecase.GetAllMarkersUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val diModule = module {

    singleOf(::PermissionManager)
    singleOf(::MarkerRepositoryImpl)

    single<GetAllMarkersUseCase>{
        GetAllMarkersUseCase(get<MarkerRepositoryImpl>())
    }

    viewModel{MapViewModel(get())}
}