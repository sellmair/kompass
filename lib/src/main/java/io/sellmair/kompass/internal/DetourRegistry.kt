package io.sellmair.kompass.internal


interface DetourRegistry : ViewDetourRegistry, FragmentDetourRegistry

internal interface SearchableDetourRegistry :
    DetourRegistry,
    SearchableViewDetourRegistry,
    SearchableFragmentDetourRegistry

internal interface ExecutableDetourRegistry :
    SearchableDetourRegistry,
    ExecutableViewDetourRegistry,
    ExecutableFragmentDetourRegistry