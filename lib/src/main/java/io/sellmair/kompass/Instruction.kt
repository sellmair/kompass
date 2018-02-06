package io.sellmair.kompass

/**
 * Created by sebastiansellmair on 29.01.18.
 */
internal enum class Instruction {

    /**
     * Will add the next destination to the backstack.
     * Popping the backstack will result in the current destination to be shown again.
     */
    ADD,

    /**
     * Will remove the current destination from the backstack and replace it
     * with the next destination.
     *
     * Popping the backstack will result in the previous screen to be shown again
     */
    REPLACE,


    /**
     * Will clear the entire backstack and start with the next destination as new 'root'
     */
    START
}