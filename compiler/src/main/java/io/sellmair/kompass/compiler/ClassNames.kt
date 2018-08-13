package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.ClassName

object ClassNames {
    val bundle = ClassName("android.os", "Bundle")
    val kompassCompanion = ClassName("io.sellmair.kompass", "Kompass.Companion")
}