package io.sellmair.kompass.app

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
    FragmentTen
}

interface TargetIdentifiable {
    val id: Target
}