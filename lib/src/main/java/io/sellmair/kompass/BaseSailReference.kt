package io.sellmair.kompass

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * Created by sebastiansellmair on 02.01.18.
 */
internal class BaseSailReference(private val baseShip: BaseShip<*>) : SailReference {

    private var activityReference = WeakReference<FragmentActivity>(null)
    private var fragmentManagerReference = WeakReference<FragmentManager>(null)
    private var cachedInstanceReference = WeakReference<KompassSail>(null)
    private var containerId: Int? = null

    override fun get(): KompassSail? {
        val activity = this.activityReference.get()
        val containerId = this.containerId
        val fragmentManager = this.fragmentManagerReference.get()

        if (activity == null || containerId == null || fragmentManager == null) return null
        return cachedInstanceReference.get() ?: run {
            val sail = BaseSail(baseShip, activity, fragmentManager, containerId)
            cachedInstanceReference = WeakReference(sail)
            sail
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