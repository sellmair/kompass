package io.sellmair.kompass.extension

import io.sellmair.kompass.Kompass

/*
################################################################################################
PUBLIC API
################################################################################################
*/


/**
 * Short form for Kompass.get("main"
 */
val <T> Kompass<T>.main get() = this["main"]