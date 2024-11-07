package com.xianyu.earthquake.koin

import com.xianyu.earthquake.http.RetrofitInstance
import org.koin.dsl.module


val koinModules = module {
    single { RetrofitInstance }
}