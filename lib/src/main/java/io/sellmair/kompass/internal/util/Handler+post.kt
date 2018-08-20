package io.sellmair.kompass.internal.util

import android.os.Handler

internal fun Handler.post(action: () -> Any) {
    this.post { action() }
}