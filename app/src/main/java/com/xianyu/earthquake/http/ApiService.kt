package com.xianyu.earthquake.http

import com.xianyu.earthquake.data.EarthData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("fdsnws/event/1/query?format=geojson")
    suspend fun getData(
        @Query("starttime") starttime: String,
        @Query("endtime") endtime: String,
        @Query("minmagnitude") minmagnitude: Int
    ): EarthData
}