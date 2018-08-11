package io.sellmair.kompass

/*
################################################################################################
PUBLIC API
################################################################################################
*/

interface KompassMap<in Destination> {
    operator fun get(destination: Destination): KompassRoute?

    companion object
}


/*
################################################################################################
INTERNAL FACTORIES
################################################################################################
*/

internal fun <Destination> KompassMap.Companion.empty(): KompassMap<Destination> = object :
    KompassMap<Destination> {
    override fun get(destination: Destination): KompassRoute? = null
}