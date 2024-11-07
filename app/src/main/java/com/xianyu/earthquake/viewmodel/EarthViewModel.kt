package com.xianyu.earthquake.viewmodel

import android.graphics.Color
import com.xianyu.earthquake.data.EarthShowData
import com.xianyu.earthquake.http.RetrofitInstance
import com.xianyu.earthquake.util.Utils
import com.xianyu.earthquake.util.Utils.MAX_LAN_LONG
import com.xianyu.earthquake.util.Utils.MIN_MAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class EarthViewModel : KoinComponent {


    // 使用 Flow 来处理数据
    fun getItemsFlow(): Flow<MutableList<EarthShowData>?> = flow {
        try {
            // 在 IO 线程中进行网络请求
            val retrofit: RetrofitInstance = get()
            val data = retrofit.apiService.getData("2023-01-01", "2024-01-01", 7)
            if (data.metadata?.status == 200) {
                val showList = mutableListOf<EarthShowData>()
                data.features.forEach {
                    val time = Utils.getTimeFromTimestamp(it.properties?.time ?: 0)
                    val color = if ((it.properties?.mag ?: 0.0) >= 7.5) {
                        Color.RED
                    } else {
                        Color.GREEN
                    }
                    showList.add(
                        EarthShowData(
                            it.properties?.place,
                            time,
                            it.properties?.mag ?: MIN_MAG,
                            color,
                            it.geometry?.coordinates?.getOrNull(0) ?: MAX_LAN_LONG,
                            it.geometry?.coordinates?.getOrNull(1) ?: MAX_LAN_LONG
                        )
                    )
                }
                emit(showList)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }
    }.flowOn(Dispatchers.IO) // 确保网络请求在 IO 线程进行
}