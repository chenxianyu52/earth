package com.xianyu.earthquake.data

import com.google.gson.annotations.SerializedName

data class EarthData(
    @SerializedName("type") val type: String? = null,
    @SerializedName("metadata") val metadata: Metadata? = Metadata(),
    @SerializedName("features") val features: ArrayList<Features> = arrayListOf(),
    @SerializedName("bbox") val bbox: ArrayList<Double> = arrayListOf()
)
