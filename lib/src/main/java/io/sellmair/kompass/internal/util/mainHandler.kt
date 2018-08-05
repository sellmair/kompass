package io.sellmair.kompass.internal.util

import android.os.Handler
import android.os.Looper

internal val mainHandler by lazy { Handler(Looper.getMainLooper()) }