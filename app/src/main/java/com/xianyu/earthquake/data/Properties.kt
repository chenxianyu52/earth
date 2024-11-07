package com.xianyu.earthquake.data

import com.google.gson.annotations.SerializedName


data class Properties(
    @SerializedName("mag") val mag: Double? = null,
    @SerializedName("place") val place: String? = null,
    @SerializedName("time") val time: Long? = null,
    @SerializedName("updated") val updated: Long? = null,
    @SerializedName("tz") val tz: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("detail") val detail: String? = null,
    @SerializedName("felt") val felt: Int? = null,
    @SerializedName("cdi") val cdi: Double? = null,
    @SerializedName("mmi") val mmi: Double? = null,
    @SerializedName("alert") val alert: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tsunami") val tsunami: Int? = null,
    @SerializedName("sig") val sig: Int? = null,
    @SerializedName("net") val net: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("ids") val ids: String? = null,
    @SerializedName("sources") val sources: String? = null,
    @SerializedName("types") val types: String? = null,
    @SerializedName("nst") val nst: Int? = null,
    @SerializedName("dmin") val dmin: Double? = null,
    @SerializedName("rms") val rms: Double? = null,
    @SerializedName("gap") val gap: Int? = null,
    @SerializedName("magType") val magType: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null
)