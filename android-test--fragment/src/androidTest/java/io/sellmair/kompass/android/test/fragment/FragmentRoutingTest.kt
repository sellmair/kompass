package io.sellmair.kompass.android.test.fragment

import androidx.test.espresso.Espresso
import androidx.test.rule.ActivityTestRule
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.test.FragmentHostActivity
import io.sellmair.kompass.android.test.FragmentHostRoute
import io.sellmair.kompass.core.*
import org.junit.Rule
import org.junit.Test

class FragmentRoutingTest {

    @get:Rule
    val activityRule = ActivityTestRule<FragmentHostActivity>(FragmentHostActivity::class.java)

    private val router: FragmentRouter<FragmentHostRoute> get() = FragmentHostActivity.router

    private val subRouter: FragmentRouter<FragmentHostRoute> get() = FragmentHostActivity.subRouter

    private val activity: FragmentHostActivity get() = activityRule.activity

    private val route1 = RouteOne()

    private val route2 = RouteTwo()

    private val route3 = RouteThree()

    private val route4 = RouteFour()

    private val route5 = RouteFive()

    private val route6 = RouteSix()

    private val route7 = RouteSeven()

    private val route8 = RouteEight()

    private val route9 = RouteNine()

    private val subRouteHost = SubRouteHost()

    private val subRoute1 = SubRouteOne()

    private val subRoute2 = SubRouteTwo()

    private fun setupRouter() {
        
        FragmentHostActivity.router = FragmentRouter {
            routing {
                route<RouteOne> { FragmentOne::class }
                route<RouteTwo> { FragmentTwo::class }
                route<RouteThree> { FragmentThree::class }
                route<RouteFour> { FragmentFour::class }
                route<RouteFive> { FragmentFive::class }
                route<RouteSix> { FragmentSix::class }
                route<RouteSeven> { FragmentSeven::class }
                route<RouteEight> { FragmentEight::class }
                route<RouteNine> { FragmentNine::class }
                route<SubRouteHost> { SubRouteHostFragment::class }
                route<SubRouteOne> { FragmentSubRouteOne::class }
                route<SubRouteTwo> { FragmentSubRouteTwo::class }
            }
        }

        FragmentHostActivity.subRouter = FragmentRouter {
            routing {
                route<SubRouteHost> { SubRouteHostFragment::class }
                route<SubRouteOne> { FragmentSubRouteOne::class }
                route<SubRouteTwo> { FragmentSubRouteTwo::class }
            }
        }
    }

    private fun recreate() {
        setupRouter()
        activityRule.runOnUiThread { activity.recreate() }
        Espresso.onIdle()
    }

    init {
        setupRouter()
    }


    @Test
    fun singlePush() {
        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }

    @Test
    fun pushPush() {
        router.instruction { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()
    }

    @Test
    fun pushPush_pop() {
        router.instruction { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.instruction { pop() }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }


    @Test
    fun pushPush_push_pop() {
        router.instruction { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.instruction { push(route3) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.instruction { pop() }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()
    }


    @Test
    fun pop() {
        router.instruction { pop() }
        activity.assertShowsNothing()
    }

    @Test
    fun push_pop_pop() {
        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.instruction { pop() }
        activity.assertShowsNothing()

        router.instruction { pop() }
        activity.assertShowsNothing()
    }


    @Test
    fun stack_popUntil() {
        router.instruction {
            RoutingStack.from(
                route1, route2, route3, route4, route5,
                route6, route7, route8, route9
            )
        }

        activity.assertShowsRoute(route9)
        activity.assertShowsFragment<FragmentNine>()

        router.instruction { popUntilRoute(route5) }
        activity.assertShowsRoute(route5)
        activity.assertShowsFragment<FragmentFive>()
    }

    @Test
    fun push_push_push_removeRoute2_pop_pop_pop() {
        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.instruction { push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.instruction { push(route3) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.instruction { with(elements.filterNot { it.route == route2 }) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.instruction { pop() }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.instruction { pop() }
        activity.assertShowsNothing()

        router.instruction { pop() }
        activity.assertShowsNothing()
    }

    @Test
    fun push_push_recreate_pop_pop_pop() {
        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.instruction { push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        recreate()

        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.instruction { pop() }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()


        router.instruction { pop() }
        activity.assertShowsNothing()

        router.instruction { pop() }
        activity.assertShowsNothing()
    }


    @Test
    fun push_push_finish_start_pop_push() {
        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.instruction { push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        activityRule.finishActivity()
        activityRule.launchActivity(null)

        Espresso.onIdle()
        activity.assertShowsNothing()

        router.instruction { pop() }
        activity.assertShowsNothing()

        router.instruction { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }

    @Test
    fun startSub_pushSub() {
        router.instruction { push(subRouteHost) }
        subRouter.instruction { push(subRoute1) }
        activity.assertShowsRoute(subRoute1)
        activity.assertShowsFragment<FragmentSubRouteOne>()
    }


    @Test
    fun startSub_pushSub_push() {
        router.push(subRouteHost)
        subRouter.push(subRoute2)
        activity.assertShowsRoute(subRoute2)
        activity.assertShowsFragment<FragmentSubRouteTwo>()
        router.push(route1)
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }


    @Test
    fun startSub_pushSub_clear() {
        router.push(subRouteHost)
        subRouter.push(subRoute2)
        activity.assertShowsRoute(subRoute2)
        activity.assertShowsFragment<FragmentSubRouteTwo>()
        router.clear()
        activity.assertShowsNothing()
    }

    @Test
    fun startSub_pushSub_pushSub_clear() {
        router.push(subRouteHost)

        subRouter.push(subRoute1)
        activity.assertShowsRoute(subRoute1)
        activity.assertShowsFragment<FragmentSubRouteOne>()

        subRouter.push(subRoute2)
        activity.assertShowsRoute(subRoute2)
        activity.assertShowsFragment<FragmentSubRouteTwo>()

        router.clear()
        activity.assertShowsNothing()
    }

    @Test
    fun push_pushSameTarget_pop_pop() {
        router.push(route1)
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        val newRoute1 = route1.copy(text = route1.text + "*")
        router.push(newRoute1)
        activity.assertShowsRoute(newRoute1)
        activity.assertShowsFragment<FragmentOne>()

        router.pop()
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.pop()
        activity.assertShowsNothing()

    }


    @Test
    fun startSub_pushSub_startSub_pushSub() {
        router.push(subRouteHost)

        subRouter.push(subRoute1)
        activity.assertShowsRoute(subRoute1)
        activity.assertShowsFragment<FragmentSubRouteOne>()

        val nextSubHostRoute = subRouteHost.copy(subRouteHost.text + "*")
        router.push(nextSubHostRoute)

        subRouter.push(subRoute2)
        activity.assertShowsRoute(subRoute2)
        activity.assertShowsFragment<FragmentSubRouteTwo>()

        router.clear()
        activity.assertShowsNothing()
    }
}