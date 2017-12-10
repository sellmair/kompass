package io.sellmair.kompass.app

import android.os.Bundle
import android.support.test.runner.AndroidJUnit4
import io.sellmair.kompass.app.model.SimpleParcelable
import io.sellmair.kompass.asSimpleDestination
import io.sellmair.kompass.asSimpleParcelDestination
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by sebastiansellmair on 10.12.17.
 */
@RunWith(AndroidJUnit4::class)
class BundleDestinationTest {


    @Test
    fun testSimpleDestination() {
        val id = 24
        val justALong = 100L
        val name = "Sandi"
        val source = SimpleDestination(id, justALong, name)

        val bundle = Bundle()
        SimpleDestinationSerializer.writeToBundle(source, bundle)

        val recreation = bundle.asSimpleDestination()
        Assert.assertEquals(id, recreation?.id)
        Assert.assertEquals(name, recreation?.name)

    }


    @Test
    fun testRecursiveParcelDestination() {
        val destId = 0
        val parentParcel = SimpleParcelable(1, SimpleParcelable(2, null))

        val parcelDest = SimpleParcelDestination(destId, parentParcel)
        val bundle = Bundle()
        SimpleParcelDestinationSerializer.writeToBundle(parcelDest, bundle)

        val recreation = bundle.asSimpleParcelDestination() ?: throw Throwable("Failed recreating")
        Assert.assertEquals(destId, recreation.id)
        Assert.assertEquals(1, recreation.parcel.id)
        Assert.assertEquals(2, recreation.parcel.child?.id)
    }


    @Test
    fun testIntListDestination() {
        val ids = listOf<Int>(24, 11, 10, 2)
        val name = "Sandi"
        val source = SimpleIntListDestination(name, ids)

    }

}