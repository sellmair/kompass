package io.sellmair.kompass.android

import android.os.Parcelable
import io.sellmair.kompass.core.Route
import io.sellmair.kompass.core.RoutingStack

interface ParcelableRoutingStack<T : Route> : RoutingStack<T>, Parcelable

