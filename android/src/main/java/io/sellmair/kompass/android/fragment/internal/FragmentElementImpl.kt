package io.sellmair.kompass.android.fragment.internal

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.FragmentContainer
import io.sellmair.kompass.android.fragment.FragmentElement
import io.sellmair.kompass.android.fragment.FragmentMappingMissingException
import io.sellmair.kompass.android.fragment.FragmentRoute
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import kotlin.reflect.KClass

internal class FragmentElementImpl<T : Route>(
    private val fragmentRouterConfiguration: FragmentRouterConfiguration<T>,
    private val container: FragmentContainer,
    private val element: RoutingStack.Element<T>
) :
    FragmentElement<T>,
    FragmentRouterConfiguration<T> by fragmentRouterConfiguration,
    RoutingStack.Element<T> by element {

    class Factory<T : Route>(
        private val fragmentRouterConfiguration: FragmentRouterConfiguration<T>,
        private val container: FragmentContainer
    ) : FragmentElement.Factory<T> {
        override fun invoke(element: RoutingStack.Element<T>): FragmentElement<T> {
            return FragmentElementImpl(
                fragmentRouterConfiguration = fragmentRouterConfiguration,
                container = container,
                element = element
            )
        }
    }

    override fun createFragment(): Fragment {
        val context = container.activity
        val fragmentFactory = container.fragmentManager.fragmentFactory
        val fragment = fragmentFactory.instantiate(context.classLoader, getFragmentClassNameOrThrow())
        with(fragmentRouteStorage) { fragment.attach(route) }
        return fragment
    }

    private fun getFragmentClassNameOrThrow(): String {
        return getFragmentClassOrThrow().java.canonicalName.orEmpty()
    }

    private fun getFragmentClassOrThrow(): KClass<out Fragment> {
        if (route is FragmentRoute) {
            return route.fragment
        }

        return fragmentMap[route] ?: throw FragmentMappingMissingException(route)
    }

    // TODO: Test this behaviour. Silently broke behaviours of DefaultFragmentStackPatcher
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoutingStack.Element<*>) return false
        if (this.key != other.key) return false
        if (this.route != other.route) return false
        return true
    }

    override fun hashCode(): Int {
        return element.hashCode()
    }


}