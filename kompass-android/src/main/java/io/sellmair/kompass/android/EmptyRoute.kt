package io.sellmair.kompass.android

import io.sellmair.kompass.core.Route

/**
 * Object representing an "empty" [Route].
 * This object may be used e.g. for transitions that expect a [Route] for either the "fromRoute" or "toRoute" param,
 * when there is no "fromRoute" or "toRoute" (because it may be the first route to be pushed)
 */
object EmptyRoute : Route