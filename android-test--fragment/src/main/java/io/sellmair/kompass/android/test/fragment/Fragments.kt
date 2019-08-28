package io.sellmair.kompass.android.test.fragment

import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.getRoute
import io.sellmair.kompass.android.route
import io.sellmair.kompass.android.test.FragmentHostActivity

class FragmentOne : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteOne>()
}

class FragmentTwo : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteTwo>()
}

class FragmentThree : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteThree>()
}

class FragmentFour : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteFour>()
}

class FragmentFive : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteFive>()
}

class FragmentSix : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteSix>()
}

class FragmentSeven : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteSeven>()
}

class FragmentEight : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteEight>()
}

class FragmentNine : BaseFragment() {
    override val route: BaseRoute get() = getRoute<RouteNine>()
}

class FragmentSubRouteOne : BaseFragment() {
    override val router: FragmentRouter<*> = FragmentHostActivity.subRouter
    override val route: BaseRoute by route()
}

class FragmentSubRouteTwo : BaseFragment() {
    override val router: FragmentRouter<*> = FragmentHostActivity.subRouter
    override val route: BaseRoute by route()
}
