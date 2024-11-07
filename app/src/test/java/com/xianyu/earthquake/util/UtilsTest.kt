package com.xianyu.earthquake.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@MockK(relaxUnitFun = true)
class UtilsTest {
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test getDpToPixel`() {
        val context = mockk<Context>()
        val resources = mockk<Resources>()
        val displayMetrics = spyk(DisplayMetrics())
//        val displayMetrics = mockk<DisplayMetrics>()

        every { context.resources } returns resources
        every { resources.displayMetrics } returns displayMetrics
        displayMetrics.density = 2.0f

        val pixel = Utils.getDpToPixel(context, 10)
        Assert.assertEquals(20, pixel)
    }

    @Test
    fun `test getTimeFromTimestamp less than zero or equal zero`() {
        val result1 = Utils.getTimeFromTimestamp(0L)
        Assert.assertEquals("", result1)
        val result2 = Utils.getTimeFromTimestamp(-2L)
        Assert.assertEquals("", result2)
    }

    @Test
    fun `test getTimeFromTimestamp exception`() {
        val result1 = Utils.getTimeFromTimestamp(30)
        Assert.assertEquals("01/01/1970 08:00:00", result1)
    }

    @Test
    fun `test getTimeFromTimestamp success`() {
        val result1 = Utils.getTimeFromTimestamp(1686852388572)
        Assert.assertEquals("16/06/2023 02:06:28", result1)
    }
}