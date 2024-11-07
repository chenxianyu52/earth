package com.xianyu.earthquake.data

import com.google.gson.annotations.SerializedName


data class Features(
  @SerializedName("type") val type: String? = null,
  @SerializedName("properties") val properties: Properties? = Properties(),
  @SerializedName("geometry") val geometry: Geometry? = Geometry(),
  @SerializedName("id") val id: String? = null
)