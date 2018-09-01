package io.sellmair.kompass.internal

import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import io.sellmair.kompass.Kompass
import io.sellmair.kompass.builder
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class KompassImplTest {

    @UiThreadTest
    @Test
    fun get_isIdempotent() {
        val kompass = Kompass.builder<Any>().build()
        val x = kompass["test"]
        val y = kompass["test"]

        assertEquals(x, y)
    }

    @Test
    fun get_fromBackgroundThread() {
        val kompass = Kompass.builder<Any>().build()
        kompass["test"]
    }

}