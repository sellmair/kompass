package io.sellmair.kompass.android.test.fragment

import androidx.test.rule.ActivityTestRule
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.test.FragmentHostActivity
import io.sellmair.kompass.android.test.FragmentHostRoute
import io.sellmair.kompass.core.RoutingStack
import io.sellmair.kompass.core.pop
import io.sellmair.kompass.core.popUntilRoute
import io.sellmair.kompass.core.push
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FragmentRoutingTest {

    @get:Rule
    val activityRule = ActivityTestRule<FragmentHostActivity>(
        FragmentHostActivity::class.java
    )

    private val router: FragmentRouter<FragmentHostRoute> get() = activityRule.activity.router

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

    @Before
    fun setup() {
        activityRule.activity.router = FragmentRouter {
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
            }
        }
    }

    @Test
    fun singlePush() {
        router.execute { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }

    @Test
    fun pushPush() {
        router.execute { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()
    }

    @Test
    fun pushPush_pop() {
        router.execute { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.execute { pop() }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()
    }


    @Test
    fun pushPush_push_pop() {
        router.execute { push(route1).push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.execute { push(route3) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.execute { pop() }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()
    }


    @Test
    fun pop() {
        router.execute { pop() }
        activity.assertShowsNothing()
    }

    @Test
    fun push_pop_pop() {
        router.execute { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.execute { pop() }
        activity.assertShowsNothing()

        router.execute { pop() }
        activity.assertShowsNothing()
    }


    @Test
    fun stack_popUntil() {
        router.execute {
            RoutingStack.from(
                route1, route2, route3, route4, route5,
                route6, route7, route8, route9
            )
        }

        activity.assertShowsRoute(route9)
        activity.assertShowsFragment<FragmentNine>()

        router.execute { popUntilRoute(route5) }
        activity.assertShowsRoute(route5)
        activity.assertShowsFragment<FragmentFive>()
    }

    @Test
    fun push_push_push_removeRoute2_pop_pop_pop() {
        router.execute { push(route1) }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.execute { push(route2) }
        activity.assertShowsRoute(route2)
        activity.assertShowsFragment<FragmentTwo>()

        router.execute { push(route3) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.execute { with(elements.filterNot { it.route == route2 }) }
        activity.assertShowsRoute(route3)
        activity.assertShowsFragment<FragmentThree>()

        router.execute { pop() }
        activity.assertShowsRoute(route1)
        activity.assertShowsFragment<FragmentOne>()

        router.execute { pop() }
        activity.assertShowsNothing()

        router.execute { pop() }
        activity.assertShowsNothing()

    }
}