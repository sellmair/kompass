package io.sellmair.kompass.android.fragment.dsl

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.EmptyFragmentMap
import io.sellmair.kompass.android.fragment.FragmentMap
import io.sellmair.kompass.android.fragment.plus
import io.sellmair.kompass.core.Route
import kotlin.reflect.KClass

@FragmentRouterDsl
class FragmentMapBuilder<T : Route> {

    private var fragmentMap: FragmentMap<T> = EmptyFragmentMap()

    @FragmentRouterDsl
    inline fun <reified R : T> route(noinline mapping: R.() -> KClass<out Fragment>?) {
        add(LambdaFragmentMap(R::class, mapping))
    }

    @FragmentRouterDsl
    fun add(fragmentMap: FragmentMap<T>) {
        this.fragmentMap += fragmentMap
    }

    @FragmentRouterDsl
    operator fun FragmentMap<T>.unaryPlus() {
        add(this)
    }

    internal fun build(): FragmentMap<T> {
        return fragmentMap
    }
}


