package io.sellmair.kompass.android.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.sellmair.kompass.android.EmptyRoute
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.contains

// TODO: Tests
object DefaultFragmentStackPatcher : FragmentStackPatcher {

    override fun invoke(
        transition: FragmentTransition,
        container: FragmentContainer, oldStack: RoutingStack<*>, newStack: FragmentRoutingStack<*>
    ) {
        container.fragmentManager.beginTransaction().disallowAddToBackStack().apply {
            patchTopElement(transition = transition, container = container, oldStack = oldStack, newStack = newStack)
            patchRemovedElements(container = container, oldStack = oldStack, newStack = newStack)
            commitNowAllowingStateLoss()
        }
    }


    private fun FragmentTransaction.patchTopElement(
        transition: FragmentTransition,
        container: FragmentContainer, oldStack: RoutingStack<*>, newStack: FragmentRoutingStack<*>
    ) {

        val oldElement = oldStack.elements.lastOrNull()
        val newElement = newStack.elements.lastOrNull()

        val oldFragment: Fragment?
        val newFragment: Fragment?

        /* No patch required. Top element is the same */
        if (oldElement?.key == newElement?.key) {
            return
        }

        /* Remove old element from screen */
        if (oldElement != null) {
            oldFragment = container.findFragmentOrThrow(oldElement)
            if (oldElement in newStack) {
                detach(oldFragment)
            }
        } else {
            oldFragment = null
        }

        /* Properly setup transition, if no new element is given */
        if (newElement == null) {
            /*
            oldFragment and oldElement can only be null if oldElement was also null.
            oldElement==null and newElement==null are eagerly returned
             */
            requireNotNull(oldElement)
            requireNotNull(oldFragment)
            return transition.setup(
                transaction = this,
                exitFragment = oldFragment,
                exitRoute = oldElement.route,
                enterFragment = EmptyFragment.instance,
                enterRoute = EmptyRoute
            )
        }


        /* Bring newElement to screen */
        val existingNewFragment = container.findFragmentOrNull(newElement)

        when {
            existingNewFragment != null && existingNewFragment.isRemoving -> {
                newFragment = newElement.createFragment()
                replace(container.id, newFragment, newElement.key.value)
            }

            existingNewFragment != null && existingNewFragment.isDetached -> {
                newFragment = existingNewFragment
                attach(newFragment)
            }

            existingNewFragment != null && !existingNewFragment.isDetached -> {
                remove(existingNewFragment)
                newFragment = newElement.createFragment()
                add(container.id, newFragment, newElement.key.value)
            }

            else -> {
                newFragment = newElement.createFragment()
                add(container.id, newFragment, newElement.key.value)
            }
        }

        /* Setup transition */
        return transition.setup(
            transaction = this,
            exitFragment = oldFragment ?: EmptyFragment.instance,
            exitRoute = oldElement?.route ?: EmptyRoute,
            enterFragment = newFragment,
            enterRoute = newElement.route
        )

    }


    private fun FragmentTransaction.patchRemovedElements(
        container: FragmentContainer, oldStack: RoutingStack<*>, newStack: FragmentRoutingStack<*>
    ) {
        oldStack.elements.filter { element -> element !in newStack }.forEach { removedElement ->
            val fragment = container.findFragmentOrNull(removedElement)
            if (fragment != null) {
                remove(fragment)
            }
        }
    }


    private fun FragmentContainer.findFragmentOrNull(element: RoutingStack.Element<*>): Fragment? {
        return fragmentManager.findFragmentByTag(element.key.value)
    }

    private fun FragmentContainer.findFragmentOrThrow(element: RoutingStack.Element<*>): Fragment {
        return findFragmentOrNull(element) ?: throw IllegalStateException("Missing fragment for $element")
    }


}


