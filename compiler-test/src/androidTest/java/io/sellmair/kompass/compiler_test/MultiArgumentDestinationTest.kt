package io.sellmair.kompass.compiler_test

import android.support.test.runner.AndroidJUnit4
import io.sellmair.kompass.asBundle
import io.sellmair.kompass.asMultiArgumentDestination
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MultiArgumentDestinationTest {

    @Test
    fun toBundleToDestination() {
        val destination = MultiArgumentDestination(
            myInt = 42,
            myFloat = 108.5f,
            mySerializable = MySerializable(),
            myParcelable = MyParcelable(),
            myDoubles = listOf(0.0, 0.5),
            myStrings = null)

        val bundle = destination.asBundle()
        val fromBundle = bundle.asMultiArgumentDestination()

        Assert.assertEquals(destination.myInt, fromBundle.myInt)
        Assert.assertEquals(destination.myFloat, fromBundle.myFloat, 0.01f)
        Assert.assertEquals(destination.myDoubles?.size, fromBundle.myDoubles?.size)
        Assert.assertNull(fromBundle.myStrings)
    }
}