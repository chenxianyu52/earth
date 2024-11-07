package com.xianyu.earthquake.util

import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date

object Utils {
    fun getDpToPixel(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeFromTimestamp(timestamp: Long): String {
        if (timestamp <= 0L) {
            return ""
        }
        return try {
            SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(timestamp))
        } catch (e: Exception) {
            ""
        }
    }

    const val MAX_LAN_LONG = 700.0
    const val MIN_MAG = -1.0
}