package io.sellmair.kompass.compiler.detour.tree

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import io.sellmair.kompass.compiler.common.RenderContext
import io.sellmair.kompass.compiler.common.RenderTree
import io.sellmair.kompass.compiler.detour.DetourElement

class DetoursRenderTree(override val context: RenderContext,
                        elements: List<DetourElement>) : RenderTree {
    interface DetourRenderTreeChild : RenderTree {
        val root: DetoursRenderTree
    }

    fun child(): DetourRenderTreeChild {
        return object : DetourRenderTreeChild {
            override val root: DetoursRenderTree = this@DetoursRenderTree
            override val context: RenderContext = root.context
        }
    }


    inner class KompassBuilderExtensions :
        DetourRenderTreeChild by child() {
        val file = FileSpec.builder("io.sellmair.kompass", "KompassBuilder_autoDetour")
        val autoDetour = FunSpec.builder("autoDetour")
    }

    inner class DetourRenderTree(val element: DetourElement) : DetourRenderTreeChild by child()

    val detours = elements.map(::DetourRenderTree)

    val kompassBuilderExtensions = KompassBuilderExtensions()

}