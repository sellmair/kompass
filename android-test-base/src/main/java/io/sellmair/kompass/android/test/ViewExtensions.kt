package io.sellmair.kompass.android.test

import android.view.View
import android.view.ViewGroup

val View.children
    get() = when (this) {
        is ViewGroup -> this.children
        else -> emptyList<View>()
    }

val ViewGroup.children
    get() = 0.until(childCount).asSequence()
        .map { getChildAt(it) }.toList()


fun View.flatViewHierarchy(): List<View> {
    return listOf(this).plus(children.flatMap { it.flatViewHierarchy() })
}