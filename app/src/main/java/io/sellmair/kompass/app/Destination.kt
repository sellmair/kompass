package io.sellmair.kompass.app

sealed class Destination(val key: String) : TargetIdentifiable {
    class One(key: String) : Destination(key)
    class Two(key: String) : Destination(key)
    class Three(key: String) : Destination(key)
    class Four(key: String) : Destination(key)
    class Five(key: String) : Destination(key)
    class Six(key: String) : Destination(key)
    class Seven(key: String) : Destination(key)
    class Eight(key: String) : Destination(key)
    class Nine(key: String) : Destination(key)
    class Ten(key: String) : Destination(key)


    override val id: Target
        get() = when (this) {
            is Destination.One -> Target.FragmentOne
            is Destination.Two -> Target.FragmentTwo
            is Destination.Three -> Target.FragmentThree
            is Destination.Four -> Target.FragmentFour
            is Destination.Five -> Target.FragmentFive
            is Destination.Six -> Target.FragmentSix
            is Destination.Seven -> Target.FragmentSeven
            is Destination.Eight -> Target.FragmentEight
            is Destination.Nine -> Target.FragmentNine
            is Destination.Ten -> Target.FragmentTen
        }
}