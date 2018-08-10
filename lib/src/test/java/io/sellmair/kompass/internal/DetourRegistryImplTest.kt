package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassViewAnimation
import io.sellmair.kompass.KompassViewDetour
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetourRegistryImplTest {

    private lateinit var registry: DetourRegistry
    private lateinit var detour: KompassViewDetour<Any, PseudoSuper, PseudoImpl>
    private lateinit var noise: KompassViewDetour<String, String, String>

    @Before
    fun setup() {

        registry = DetourRegistryImpl()


        detour = object : KompassViewDetour<Any, PseudoSuper, PseudoImpl>() {
            override fun setup(
                destination: Any,
                currentView: PseudoSuper,
                nextView: PseudoImpl):
                KompassViewAnimation = object : KompassViewAnimation {
                override val duration = 500L

            }
        }

        noise = object : KompassViewDetour<String, String, String>() {
            override fun setup(
                destination: String,
                currentView: String,
                nextView: String) = TODO()
        }

        registry.add(detour)
        registry.add(noise)
    }

    @Test
    fun cantFindSuperType() {
        val found: KompassViewDetour<Any, PseudoSuper, PseudoSuper>? = registry.findViewDetour()
        Assert.assertNull(found)
    }

    @Test
    fun findsExactType() {
        val found: KompassViewDetour<Any, PseudoSuper, PseudoImpl>? = registry.findViewDetour()
        Assert.assertEquals(found, detour)
    }

    @Test
    fun findsSubType() {
        val found: KompassViewDetour<Any, PseudoSuper, PseudoImplImpl>? = registry.findViewDetour()
        Assert.assertEquals(found, detour)
    }


    @Test
    fun setupViewDetour() {
        val animation = registry.setupViewDetour(
            destination = PseudoDestination.instance,
            current = PseudoSuper.instance,
            next = PseudoImpl.instance)

        Assert.assertEquals(500L, animation?.duration)
    }

    @Test
    fun setupViewDetour_withSubType() {
        val animation = registry.setupViewDetour(
            destination = PseudoDestination.instance,
            current = PseudoSuper.instance,
            next = PseudoImplImpl.instance)

        Assert.assertEquals(500L, animation?.duration)
    }

    @Test
    fun setupViewDetour_withSupertype() {
        val animation = registry.setupViewDetour(
            destination = PseudoDestination.instance,
            current = PseudoSuper.instance,
            next = PseudoSuper.instance)

        Assert.assertNull(animation)
    }

}

private interface PseudoDestination {
    companion object {
        val instance = object : PseudoDestination {}
    }
}

private interface PseudoSuper {
    companion object {
        val instance = object : PseudoSuper {}
    }
}

private interface PseudoImpl : PseudoSuper {
    companion object {
        val instance = object : PseudoImpl {}
    }
}

private interface PseudoImplImpl : PseudoImpl {
    companion object {
        val instance = object : PseudoImplImpl {}
    }
}

