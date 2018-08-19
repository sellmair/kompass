package io.sellmair.kompass.compiler

import com.squareup.kotlinpoet.ClassName

object ClassNames {
    val activity = ClassName("android.app", "Activity")
    val fragment = ClassName("android.support.v4.app", "Fragment")
    val view = ClassName("android.view", "View")

    val kompassCompanion = ClassName("io.sellmair.kompass", "Kompass.Companion")
    val kompassRoute = ClassName("io.sellmair.kompass", "KompassRoute")
    fun kompassBuilder(type: String) = ClassName("io.sellmair.kompass", "KompassBuilder<$type>")
    fun kompassMap(type: String) = ClassName("io.sellmair.kompass", "KompassMap<$type>")
    fun kompassCrane(type: String) = ClassName("io.sellmair.kompass", "KompassCrane<$type>")

    val bundle = ClassName("android.os", "Bundle")
    val parcelable = ClassName("android.os", "Parcelable")
    val serializable = ClassName("java.io", "Serializable")
    val list = ClassName("java.util", "List")

    val boolean = ClassName("java.lang", "Boolean")
    val byte = ClassName("java.lang", "Byte")
    val char = ClassName("java.lang", "Character")
    val short = ClassName("java.lang", "Short")
    val integer = ClassName("java.lang", "Integer")
    val long = ClassName("java.lang", "Long")
    val float = ClassName("java.lang", "Float")
    val double = ClassName("java.lang", "Double")
    val string = ClassName("java.lang", "String")


    val javaObject = ClassName("java.lang", "Object")
    val any = ClassName("kotlin", "Any")

}

