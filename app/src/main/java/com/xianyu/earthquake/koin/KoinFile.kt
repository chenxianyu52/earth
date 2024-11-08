package com.xianyu.earthquake.koin

import com.xianyu.earthquake.http.RetrofitInstance
import com.xianyu.earthquake.viewmodel.EarthViewModel
import org.koin.dsl.module


val koinModules = module {
    single { RetrofitInstance }
    single { EarthViewModel() }
}