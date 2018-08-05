package io.sellmair.kompass.internal

import io.sellmair.kompass.KompassViewAnimation
import io.sellmair.kompass.KompassViewDetour
import org.junit.Assert
import org.junit.Test

class DetourRegistryImplTest {

    @Test
    fun findsSuperType() {
        val registry = DetourRegistryImpl()
        val detour = object : KompassViewDetour<Any, PseudoSuper, PseudoImpl>() {
            override fun setup(destination: Any, currentView: PseudoSuper, nextView: PseudoImpl):
                KompassViewAnimation {
                TODO()
            }

        }

        registry.add(detour)

        val found: KompassViewDetour<Any, PseudoSuper, PseudoImpl>? = registry.findViewDetour()

        Assert.assertNotNull(found)
    }
}

private interface PseudoDestination
private interface PseudoSuper
private interface PseudoImpl : PseudoSuper