package io.sellmair.kompass.android.test.fragment

import io.sellmair.kompass.android.test.FragmentHostRoute

interface BaseRoute : FragmentHostRoute {
    val text: String get() = this.javaClass.simpleName
}