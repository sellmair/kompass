package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import io.sellmair.kompass.util.SailReference
import java.lang.ref.WeakReference

/**
 * Created by sebastiansellmair on 02.01.18.
 *
 * This implementation will hold weak-references to each component of a Kompass-Sail
 * and reassemble it if all of this components are not yet collected by the gc.
 *
 * This is because just holding a weak-reference to any [KompassSail] won't prevent
 * the sail from being collected, because we cannot expect the sail to be held strongly anywhere,
 * because this would lead to a memory leak (by leaking the activity).
 *
 * Rather we need to hold weak references to all components of the ship and
 * reassemble an instance, when needed and possible.
 */
internal class BaseSailReference(private val baseShip: BaseShip<*>) : SailReference {

    private var activityReference = WeakReference<FragmentActivity>(null)
    private var fragmentManagerReference = WeakReference<FragmentManager>(null)

    /**
     * The latest instance of the KompassSail can also be cached here.
     * This is just a micro-performance tweak and will lead to the same sail
     * object being returned between two gc-cycles.
     */
    private var cachedInstanceReference = WeakReference<KompassSail>(null)

    /**
     * The container id is not necessary to be held weakly, obviously ^^
     */
    private var containerId: Int? = null


    override fun get(): KompassSail? {

        val activity = this.activityReference.get()
        val containerId = this.containerId
        val fragmentManager = this.fragmentManagerReference.get()

        if (activity == null || containerId == null || fragmentManager == null) return null
        return cachedInstanceReference.get() ?: run {
            val sail = BaseSail(baseShip, activity, fragmentManager, containerId)
            cachedInstanceReference = WeakReference(sail)
            return@run sail
        }
    }

    override fun set(sail: KompassSail?) {
        if (sail == null) return release()
        this.activityReference = WeakReference<FragmentActivity>(sail.activity)
        this.fragmentManagerReference = WeakReference(sail.fragmentManager)
        this.containerId = sail.containerId
        this.cachedInstanceReference = WeakReference(sail)

    }

    override fun release() {
        this.cachedInstanceReference = WeakReference<KompassSail>(null)
        this.activityReference = WeakReference<FragmentActivity>(null)
        this.containerId = null
    }

}