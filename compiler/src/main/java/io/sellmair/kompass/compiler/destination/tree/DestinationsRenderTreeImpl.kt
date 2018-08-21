package io.sellmair.kompass.compiler.destination.tree

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.destination.DestinationElement


internal fun DestinationsRenderTree.Companion.from(context: RenderContext, elements: List<DestinationElement>):
    DestinationsRenderTree = DestinationsRenderTreeImpl(context, elements)


internal class DestinationsRenderTreeImpl(
    override val context: RenderContext,
    elements: List<DestinationElement>) : DestinationsRenderTree {

    override val destinations = elements.map { DestinationRenderTreeImpl(context, it) }

    override val autoCrane = AutoCraneRenderTreeImpl(context)

    override val autoMap = AutoMapRenderTreeImpl(context)

    override val kompassBuilderExtensions = KompassBuilderExtensionsRenderTreeImpl(context)

}

internal class DestinationRenderTreeImpl(override val context: RenderContext,
                                         override val element: DestinationElement) :
    DestinationRenderTree {

    override val extensions: ExtensionRenderTree =
        ExtensionRenderTreeImpl(context, element)
}

class AutoCraneRenderTreeImpl(override val context: RenderContext) : AutoCraneRenderTree {

    override val file: FileSpec.Builder = FileSpec.builder("io.sellmair.kompass", "AutoCrane")

    override val type: TypeSpec.Builder = TypeSpec.classBuilder("AutoCrane")

    override val get: FunSpec.Builder = FunSpec.builder("get")
}

class AutoMapRenderTreeImpl(override val context: RenderContext) : AutoMapRenderTree {

    override val file: FileSpec.Builder = FileSpec.builder("io.sellmair.kompass", "AutoMap")

    override val type: TypeSpec.Builder = TypeSpec.classBuilder("AutoMap")

    override val get: FunSpec.Builder = FunSpec.builder("get")
}


class KompassBuilderExtensionsRenderTreeImpl(
    override val context: RenderContext) : KompassBuilderExtensionsRenderTree {

    override val file: FileSpec.Builder =
        FileSpec.builder("io.sellmair.kompass", "KompassBuilder_auto")

    override val autoCrane: FunSpec.Builder = FunSpec.builder("autoCrane")

    override val autoMap: FunSpec.Builder = FunSpec.builder("autoMap")

}

internal class ExtensionRenderTreeImpl(
    override val context: RenderContext,
    element: DestinationElement) : ExtensionRenderTree {

    override val file: FileSpec.Builder =
        FileSpec.builder("io.sellmair.kompass", "${element.simpleName}Extensions")

    override val kompassCompanionExtensions: KompassCompanionExtensionRenderTree =
        KompassCompanionExtensionsRenderTreeImpl(context, element)

    override val bundleExtensions: BundleExtensionsRenderTree =
        BundleExtensionsRenderTreeImpl(context, element)

    override val destinationExtensions: DestinationExtensionsRenderTree =
        DestinationExtensionsRenderTreeImpl(context)

}

internal class KompassCompanionExtensionsRenderTreeImpl(
    override val context: RenderContext,
    element: DestinationElement) : KompassCompanionExtensionRenderTree {

    override val destinationAsBundle: FunSpec.Builder =
        FunSpec.builder("${element.simpleName.toString().decapitalize()}AsBundle")


    override val bundleAsDestination: FunSpec.Builder =
        FunSpec.builder("bundleAs${element.simpleName}")

}

internal class BundleExtensionsRenderTreeImpl(
    override val context: RenderContext,
    element: DestinationElement) : BundleExtensionsRenderTree {

    override val asDestination: FunSpec.Builder =
        FunSpec.builder("as${element.simpleName}")

    override val tryAsDestination: FunSpec.Builder =
        FunSpec.builder("tryAs${element.simpleName}")

}

class DestinationExtensionsRenderTreeImpl(
    override val context: RenderContext) : DestinationExtensionsRenderTree {

    override val asBundle: FunSpec.Builder = FunSpec.builder("asBundle")

}


