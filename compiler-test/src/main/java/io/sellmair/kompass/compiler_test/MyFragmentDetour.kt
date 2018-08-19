package io.sellmair.kompass.compiler_test

import android.support.v4.app.FragmentTransaction
import io.sellmair.kompass.KompassFragmentDetour
import io.sellmair.kompass.annotation.Detour

@Detour
class MyFragmentDetour : KompassFragmentDetour<Any, Any, Any> {
    override fun setup(
        destination: Any,
        currentFragment: Any,
        nextFragment: Any,
        transaction: FragmentTransaction) {
    }

}