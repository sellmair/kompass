package io.sellmair.kompass.exception

/**
 * Created by sebastiansellmair on 02.01.18.
 */
class MissingBundleSerializerException(destination: Class<*>) : Exception("" +
        "No bundle-serializer found for type ${destination.simpleName} " +
        "\n" +
        "\n" +
        "Possible solutions: \n" +
        "- Annotate the class with @Destination if you are using the Kompass-Compiler\n" +
        "- Implement the interface KompassDestination\n" +
        "- Implement the serializing logic inside your KompassCrane") {

    constructor(obj: Any) : this(obj::class.java)
}