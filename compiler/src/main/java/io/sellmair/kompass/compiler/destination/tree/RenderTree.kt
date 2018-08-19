package io.sellmair.kompass.compiler.destination.tree

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import io.sellmair.kompass.compiler.destination.DestinationConstructorElement
import io.sellmair.kompass.compiler.destination.DestinationElement
import io.sellmair.kompass.compiler.destination.RenderContextSensitive

/*
Example thoughts on a element called LoginDestination
What should be generated
1. We need a function Kompass.loginDestinationAsBundle()
2. We need a function Kompass.bundleAsLoginDestination()
3. We want a function LoginDestination.asBundle()
4. We want a function Bundle?.asLoginDestination()
5. We want a function Bundle?.tryAsLoginDestination()
6. We need a class AutoCrane (which utilizes our extension functions)
7. We want a function KompassBuilder.autoCrane()

Connections between those things:

Kompass.loginDestinationAsBundle() can just be created.
Kompass.bundleToLoginDestination() can just be created.

LoginDestination.asBundle() !!! needs to know Kompass.loginDestinationAsBundle !!!
Bundle?.(try)AsLoginDestination() !!! needs to know Kompass.bundleAsLoginDestination !!!

AutoCrane !!! needs to know extension functions !!!

KompassBuilder.autoCrane() !!! needs to know AutoCrane !!!

 */


interface RenderTree : RenderContextSensitive {
    companion object
}

interface DestinationsRenderTree : RenderTree {
    val destinations: List<DestinationRenderTree>
    val autoCrane: AutoCraneRenderTree
    val kompassBuilderExtensions: KompassBuilderExtensionsRenderTree

    companion object
}

interface AutoCraneRenderTree : RenderTree {
    val file: FileSpec.Builder
    val type: TypeSpec.Builder
    val get: FunSpec.Builder
}

interface DestinationRenderTree : RenderTree {
    val element: DestinationElement
    val constructor: DestinationConstructorElement
    val extensions: ExtensionRenderTree
}


interface ExtensionRenderTree : RenderTree {
    val file: FileSpec.Builder
    val kompassCompanionExtensions: KompassCompanionExtensionRenderTree
    val bundleExtensions: BundleExtensionsRenderTree
    val destinationExtensions: DestinationExtensionsRenderTree

}

interface KompassCompanionExtensionRenderTree : RenderTree {
    val destinationAsBundle: FunSpec.Builder
    val bundleAsDestination: FunSpec.Builder
}

interface BundleExtensionsRenderTree : RenderTree {
    val asDestination: FunSpec.Builder
    val tryAsDestination: FunSpec.Builder
}

interface DestinationExtensionsRenderTree : RenderTree {
    val asBundle: FunSpec.Builder
}

interface KompassBuilderExtensionsRenderTree : RenderTree {
    val file: FileSpec.Builder
    val autoCrane: FunSpec.Builder
}
