package io.sellmair.kompass.android.fragment

import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.exception.KompassException

class FragmentMappingMissingException(route: Route) : KompassException(
    """
    Missing fragment mapping for route $route.
    Consider implementing `FragmentRoute`, specifying a `FragmentMap` or declaring it via DSL:
    FragmentRouter { 
        routing {
            route<${route::class.java.simpleName}> { [TODO] }
        }
    }
""".trimIndent()
)