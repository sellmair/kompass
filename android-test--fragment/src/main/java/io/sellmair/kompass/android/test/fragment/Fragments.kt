package io.sellmair.kompass.android.test.fragment

import io.sellmair.kompass.android.getRoute

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


