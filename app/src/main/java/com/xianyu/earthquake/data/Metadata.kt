package com.xianyu.earthquake.data

import com.google.gson.annotations.SerializedName


data class Metadata(
    @SerializedName("generated") val generated: Long? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("api") val api: String? = null,
    @SerializedName("count") val count: Int? = null
)