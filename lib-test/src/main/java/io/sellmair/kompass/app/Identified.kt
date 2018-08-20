package io.sellmair.kompass.app

import android.graphics.Color

enum class Target {
    FragmentOne,
    FragmentTwo,
    FragmentThree,
    FragmentFour,
    FragmentFive,
    FragmentSix,
    FragmentSeven,
    FragmentEight,
    FragmentNine,
    FragmentTen,

    ViewOne,
    ViewTwo,
    ViewThree,
    ViewFour,
    ViewFive
}

interface TargetIdentifiable {
    val id: Target
}

val Target.color: Int
    get() {
        val targets = Target.values().size
        val hueMax = 360f
        val hue = ordinal.toFloat() / targets.toFloat() * hueMax
        val saturation = 0.8f
        val value = 0.8f

        val hsv = floatArrayOf(hue, saturation, value)
        return Color.HSVToColor(hsv)
    }
