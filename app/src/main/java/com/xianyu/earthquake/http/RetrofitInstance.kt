package com.xianyu.earthquake.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://earthquake.usgs.gov/"

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)   // 设置连接超时
            .readTimeout(30, TimeUnit.SECONDS)      // 设置读取超时
            .writeTimeout(30, TimeUnit.SECONDS)     // 设置写入超时
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY   // 添加日志拦截器，记录请求和响应
            })
            .build()
    }

    private val gsonConverterFactory: GsonConverterFactory by lazy {
        GsonConverterFactory.create()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory) // Gson 转换器
            .client(httpClient) // OkHttpClient 配置
            .build()
            .create(ApiService::class.java)
    }
}