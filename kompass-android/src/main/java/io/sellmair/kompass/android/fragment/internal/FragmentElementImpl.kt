package io.sellmair.kompass.android.fragment.internal

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.FragmentContainer
import io.sellmair.kompass.android.fragment.FragmentElement
import io.sellmair.kompass.android.fragment.FragmentMappingMissingException
import io.sellmair.kompass.android.fragment.FragmentRoute
import io.sellmair.kompass.core.Key
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack
import kotlin.reflect.KClass

internal class FragmentElementImpl<T : Route>(
    private val fragmentRouterConfiguration: FragmentRouterConfiguration<T>,
    private val container: FragmentContainer,
    private val element: RoutingStack.Element<T>
) :
    FragmentElement<T>(),
    FragmentRouterConfiguration<T> by fragmentRouterConfiguration {

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

    override val key: Key = element.key

    override val route: T = element.route

    override fun createFragment(): Fragment {
        val context = container.activity
        val fragmentFactory = container.fragmentManager.fragmentFactory
        val fragment = fragmentFactory.instantiate(context.classLoader, getFragmentClassNameOrThrow())
        fragmentRouteStorageSyntax.run { fragment.attach(route) }
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

}