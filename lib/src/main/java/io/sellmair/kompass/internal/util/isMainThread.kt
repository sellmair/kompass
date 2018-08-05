package io.sellmair.kompass.internal.util

import android.os.Looper

internal val isMainThread: Boolean get() = Looper.myLooper() == Looper.getMainLooper()