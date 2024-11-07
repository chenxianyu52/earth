package com.xianyu.earthquake.data

import com.google.gson.annotations.SerializedName


data class Geometry(
    @SerializedName("type") val type: String? = null,
    @SerializedName("coordinates") val coordinates: ArrayList<Double> = arrayListOf()
)