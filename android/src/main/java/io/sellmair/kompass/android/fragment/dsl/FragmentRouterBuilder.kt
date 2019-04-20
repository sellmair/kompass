package io.sellmair.kompass.android.fragment.dsl

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import io.sellmair.kompass.android.fragment.*
import io.sellmair.kompass.android.fragment.internal.*
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import kotlin.reflect.KClass

@FragmentRouterDsl
class FragmentRouterBuilder<T : Route>(type: KClass<T>) {

    private val typeIsParcelable = Parcelable::class.java.isAssignableFrom(type.java)

    private var initialStack: RoutingStack<T> = RoutingStack.empty()

    private var fragmentStackPatcher: FragmentStackPatcher = DefaultFragmentStackPatcher

    private var fragmentMap: FragmentMap<T> = EmptyFragmentMap()

    private var fragmentTransition: FragmentTransition = EmptyFragmentTransition

    private var fragmentRouteStorage: FragmentRouteStorage<T>? = when {
        typeIsParcelable -> ParcelableFragmentRouteStorage.createUnsafe()
        else -> null
    }

    private var fragmentRoutingStackBundler: FragmentRoutingStackBundler<T>? = when {
        typeIsParcelable -> ParcelableFragmentRoutingStackBundler.createUnsafe()
        else -> null
    }

    private var fragmentContainerLifecycleFactory: FragmentContainerLifecycle.Factory =
        GenericFragmentContainerLifecycle.Factory(
            attachEvent = Lifecycle.Event.ON_RESUME,
            detachEvent = Lifecycle.Event.ON_PAUSE
        )


    @FragmentRouterDsl
    fun fragmentStackPatcher(patcher: FragmentStackPatcher) {
        this.fragmentStackPatcher = patcher
    }

    @FragmentRouterDsl
    fun fragmentRouteStorage(storage: FragmentRouteStorage<T>) {
        this.fragmentRouteStorage = storage
    }


    @FragmentRouterDsl
    fun routing(init: FragmentMapBuilder<T>.() -> Unit) {
        this.fragmentMap += FragmentMapBuilder<T>().also(init).build()
    }


    @FragmentRouterDsl
    fun animation(init: FragmentTransitionBuilder.() -> Unit) {
        this.fragmentTransition += FragmentTransitionBuilder().also(init).build()
    }


    @PublishedApi
    internal fun build(): FragmentRouter<T> {
        return FragmentRouter(
            fragmentMap = fragmentMap,
            fragmentRouteStorage = requireFragmentRouteStorage(),
            fragmentRoutingStackBundler = requireFragmentRoutingStackBundler(),
            fragmentTransition = fragmentTransition,
            fragmentStackPatcher = fragmentStackPatcher,
            fragmentContainerLifecycleFactory = fragmentContainerLifecycleFactory,
            initialStack = initialStack
        )
    }

    private fun requireFragmentRouteStorage(): FragmentRouteStorage<T> {
        return fragmentRouteStorage ?: throw KompassFragmentDslException(
            ""
        )
    }


    private fun requireFragmentRoutingStackBundler(): FragmentRoutingStackBundler<T> {
        return fragmentRoutingStackBundler ?: throw KompassFragmentDslException(
            ""
        )
    }
}


class FragmentTransitionBuilder {

    var transition: FragmentTransition = EmptyFragmentTransition

    @FragmentRouterDsl
    fun register(transition: FragmentTransition) {
        this.transition += transition
    }

    @JvmName("registerGeneric")
    @FragmentRouterDsl
    inline fun <reified ExitFragment : Fragment, reified ExitRoute : Route,
            reified EnterFragment : Fragment, reified EnterRoute : Route> register(
        transition: GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>
    ) = register(transition.reified().erased())


    internal fun build(): FragmentTransition {
        return transition
    }
}


