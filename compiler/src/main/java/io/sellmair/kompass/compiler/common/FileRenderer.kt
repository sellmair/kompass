package io.sellmair.kompass.compiler.common

import java.io.File
import javax.tools.Diagnostic

/*
################################################################################################
PUBLIC API
################################################################################################
*/
interface FileRenderer : Renderable<AssociatedFile> {
    companion object
}

operator fun FileRenderer.Companion.invoke(context: RenderContext): FileRenderer {
    return FileRendererImpl(context)
}


/*
################################################################################################
PRIVATE IMPLEMENTATION
################################################################################################
*/

private class FileRendererImpl(val context: RenderContext) : FileRenderer {
    override fun render(target: AssociatedFile) {
        val canonicalName = "${target.spec.packageName}.${target.spec.name}"

        context.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "" +
            "Writing file $canonicalName")


        val file = context.filer.createSourceFile(canonicalName, *target.elements.toTypedArray())
        target.spec.writeTo(File(file.toUri()))
    }

}

