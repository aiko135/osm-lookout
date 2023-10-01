package com.example.osmlookout

import com.example.osmlookout.util.PermissionManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val diModule = module {

    singleOf(::PermissionManager)

}