package io.sellmair.kompass.android.utils

import android.os.Looper

internal val isMainThread: Boolean get() = Looper.getMainLooper() == Looper.myLooper()