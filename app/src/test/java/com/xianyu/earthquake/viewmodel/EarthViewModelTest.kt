package com.xianyu.earthquake.viewmodel

import android.graphics.Color
import com.xianyu.earthquake.data.EarthData
import com.xianyu.earthquake.data.Features
import com.xianyu.earthquake.data.Properties
import com.xianyu.earthquake.http.ApiService
import com.xianyu.earthquake.http.RetrofitInstance
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@MockK(relaxUnitFun = true)
class EarthViewModelTest : KoinTest {

    private lateinit var mockRetrofit: RetrofitInstance
    private var apiService: ApiService = mockk()

    @Before
    fun setup() {
        // 在测试中启动 Koin
        startKoin {
            modules(module {
                single { mockk<RetrofitInstance>() }
            })
        }
        mockRetrofit = getKoin().get()
        coEvery { mockRetrofit.apiService } returns apiService
    }

    @Test
    fun `test getItemsFlow should emit data if status is 200`() = runTest {
        // 准备模拟的数据
        val mockData = EarthData(
            metadata = com.xianyu.earthquake.data.Metadata(status = 200),
            features = mutableListOf(Features(id = "1")) as ArrayList<Features>
        )

        // 模拟 apiService 返回数据
        coEvery { apiService.getData(any(), any(), any()) } returns mockData

        val flow = EarthViewModel().getItemsFlow()
        flow.collect { result ->
            assert(result?.size == 1)
        }
    }

    @Test
    fun `test getItemsFlow should emit null if status is not 200`() = runTest {
        // 准备模拟的数据，模拟一个失败的情况
        val mockData = EarthData(
            metadata = com.xianyu.earthquake.data.Metadata(status = 400),
            features = ArrayList<Features>()
        )

        // 模拟 apiService 返回数据
        coEvery { apiService.getData(any(), any(), any()) } returns mockData

        // 调用 getItemsFlow 并收集结果
        val flow = EarthViewModel().getItemsFlow()
        flow.collect { result ->
            // 验证返回的结果应该是 null
            assert(result == null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getItemsFlow should emit null if exception occurs`() = runTest {
        // 模拟 apiService 抛出异常
        coEvery { apiService.getData(any(), any(), any()) } throws Exception("Network error")

        // 调用 getItemsFlow 并收集结果
        val flow = EarthViewModel().getItemsFlow()
        flow.collect { result ->
            // 验证返回的结果应该是 null
            assert(result == null)
        }
    }

    @Test
    fun `test getItemsFlow should emit data if mag is red`() = runTest {
        // 准备模拟的数据
        val mockData = EarthData(
            metadata = com.xianyu.earthquake.data.Metadata(status = 200),
            features = mutableListOf(
                Features(
                    id = "1",
                    properties = Properties(mag = 7.5)
                )
            ) as ArrayList<Features>
        )

        // 模拟 apiService 返回数据
        coEvery { apiService.getData(any(), any(), any()) } returns mockData

        val flow = EarthViewModel().getItemsFlow()
        flow.collect { result ->
            assert(result?.get(0)?.mag == 7.5)
            assert(result?.get(0)?.color == Color.RED)
        }
    }

    @Test
    fun `test getItemsFlow should emit data if mag is green`() = runTest {
        // 准备模拟的数据
        val mockData = EarthData(
            metadata = com.xianyu.earthquake.data.Metadata(status = 200),
            features = mutableListOf(
                Features(
                    id = "1",
                    properties = Properties(mag = 7.4)
                )
            ) as ArrayList<Features>
        )

        // 模拟 apiService 返回数据
        coEvery { apiService.getData(any(), any(), any()) } returns mockData

        val flow = EarthViewModel().getItemsFlow()
        flow.collect { result ->
            assert(result?.get(0)?.mag == 7.4)
            assert(result?.get(0)?.color == Color.GREEN)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}