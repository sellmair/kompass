package io.sellmair.kompass.internal

internal class DetourRegistryImpl :
    DetourRegistry,
    ViewDetourRegistry by ViewDetourRegistryImpl(),
    FragmentDetourRegistry by FragmentDetourRegistryImpl()