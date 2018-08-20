package io.sellmair.kompass.internal

internal class DetourRegistryImpl :
    ExecutableDetourRegistry,
    ExecutableViewDetourRegistry by ViewDetourRegistryImpl(),
    ExecutableFragmentDetourRegistry by FragmentDetourRegistryImpl()