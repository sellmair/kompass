package io.sellmair.kompass.android.fragment.dsl

import android.os.Parcelable
import androidx.lifecycle.Lifecycle
import io.sellmair.kompass.android.fragment.*
import io.sellmair.kompass.android.fragment.internal.EmptyFragmentTransition
import io.sellmair.kompass.android.fragment.internal.FragmentContainerLifecycle
import io.sellmair.kompass.android.fragment.internal.GenericFragmentContainerLifecycle
import io.sellmair.kompass.android.fragment.internal.createUnsafe
import io.sellmair.kompass.core.EmptyRouterInstruction
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RouterInstruction
import io.sellmair.kompass.core.plus
import kotlin.reflect.KClass

@FragmentRouterDsl
class FragmentRouterBuilder<T : Route>(private val type: KClass<T>) {

    private val typeIsParcelable = Parcelable::class.java.isAssignableFrom(type.java)

    private var initialInstruction: RouterInstruction<T> = EmptyRouterInstruction()

    private var fragmentStackPatcher: FragmentStackPatcher = DefaultFragmentStackPatcher

    private var fragmentMap: FragmentMap<T> = EmptyFragmentMap()

    private var fragmentTransition: FragmentTransition = EmptyFragmentTransition

    private var fragmentRouteStorageSyntax: FragmentRouteStorageSyntax<T>? = when {
        typeIsParcelable -> ParcelableFragmentRouteStorageSyntax.createUnsafe()
        else -> null
    }

    private var fragmentRoutingStackBundleSyntax: FragmentRoutingStackBundleSyntax<T>? = when {
        typeIsParcelable -> ParcelableFragmentRoutingStackBundleSyntax.createUnsafe()
        else -> null
    }

    private var fragmentContainerLifecycleFactory: FragmentContainerLifecycle.Factory =
        GenericFragmentContainerLifecycle.Factory(
            attachEvent = Lifecycle.Event.ON_RESUME,
            detachEvent = Lifecycle.Event.ON_PAUSE
        )

    /**
     * Specify a custom [FragmentStackPatcher]
     * @see FragmentStackPatcher
     * @see DefaultFragmentStackPatcher
     */
    @FragmentRouterDsl
    fun fragmentStackPatcher(patcher: FragmentStackPatcher) {
        this.fragmentStackPatcher = patcher
    }

    /**
     * Specify a custom [FragmentRouteStorageSyntax].
     *
     * [ParcelableFragmentRouteStorageSyntax] will be used as default if the base route type [T] is [Parcelable]
     *
     * @see FragmentRouteStorageSyntax
     * @see ParcelableFragmentRouteStorageSyntax
     */
    @FragmentRouterDsl
    fun fragmentRouteStorage(storage: FragmentRouteStorageSyntax<T>) {
        this.fragmentRouteStorageSyntax = storage
    }

    /**
     * Specify a custom [fragmentRoutingStackBundleSyntax]
     *
     * [ParcelableFragmentRoutingStackBundleSyntax] will be used as default if the base route type [T] is [Parcelable]
     *
     * @see FragmentRoutingStackBundleSyntax
     * @see ParcelableFragmentRouteStorageSyntax
     */
    @FragmentRouterDsl
    fun fragmentRoutingStackBundler(bundler: FragmentRoutingStackBundleSyntax<T>) {
        this.fragmentRoutingStackBundleSyntax = bundler
    }

    /**
     * Configures a [FragmentMap] that will be used by the router.
     * Can be invoked multiple times
     */
    @FragmentRouterDsl
    fun routing(init: FragmentMapBuilder<T>.() -> Unit) {
        this.fragmentMap += FragmentMapBuilder<T>().also(init).build()
    }


    @FragmentRouterDsl
    fun transitions(init: FragmentTransitionBuilder.() -> Unit) {
        this.fragmentTransition += FragmentTransitionBuilder().also(init).build()
    }

    @FragmentRouterDsl
    fun initialize(instruction: RouterInstruction<T>) {
        this.initialInstruction += instruction
    }


    @PublishedApi
    internal fun build(): FragmentRouter<T> {
        return FragmentRouter(
            fragmentMap = fragmentMap,
            fragmentRouteStorageSyntax = requireFragmentRouteStorage(),
            fragmentRoutingStackBundleSyntax = requireFragmentRoutingStackBundler(),
            fragmentTransition = fragmentTransition,
            fragmentStackPatcher = fragmentStackPatcher,
            fragmentContainerLifecycleFactory = fragmentContainerLifecycleFactory,
            initialInstruction = initialInstruction
        )
    }

    private fun requireFragmentRouteStorage(): FragmentRouteStorageSyntax<T> {
        return fragmentRouteStorageSyntax ?: throw KompassFragmentDslException(
            """
                Missing `FragmentRouteStorageSyntax`!
                Either specify one with

                    FragmentRouter {
                        ...
                        fragmentRouteStorage(MyFragmentRouteStorageSyntax())
                    }

                Or let ${type.java.simpleName} implement `Parcelable` to use the default implementation
            """.trimIndent()
        )
    }


    private fun requireFragmentRoutingStackBundler(): FragmentRoutingStackBundleSyntax<T> {
        return fragmentRoutingStackBundleSyntax ?: throw KompassFragmentDslException(
            """
                Missing `FragmentRoutingStackBundleSyntax`!
                Either specify one with

                    FragmentRouter {
                        ...
                        fragmentRoutingStackBundler(MyFragmentRoutingStackBundleSyntax())
                    }

                Or let ${type.java.simpleName} implement `Parcelable` to use the default implementation
            """.trimIndent()
        )
    }
}
