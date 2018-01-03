package io.sellmair.kompass.exception

/**
 * Created by sebastiansellmair on 03.01.18.
 */
class MissingMapElementException(destination: Any) : Exception("" +
        "No map entry found for destination :${destination.javaClass.simpleName}" +
        "\n" +
        "\n" +
        "Possible solutions: \n" +
        "- Add 'target' parameter to the destination's annotation\n" +
        "- Implement / Complete your custom KompassMap"
)