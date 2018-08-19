package io.sellmair.kompass.extension

import android.support.v4.app.Fragment

fun Fragment.resetTransitions() {
    this.enterTransition = null
    this.reenterTransition = null
    this.exitTransition = null
    this.returnTransition = null
}