package com.xianyu.earthquake.data

/**
 * just show data
 */
data class EarthShowData(
    val place: String? = null,
    val time: String? = null,
    val mag: Double? = null,
    val color: Int,
    val longitude: Double,
    val latitude: Double
)
