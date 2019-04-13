package io.sellmair.kompass.android.test.fragment

import android.widget.TextView
import androidx.test.espresso.Espresso
import io.sellmair.kompass.android.test.FragmentHostActivity
import org.junit.Assert.assertNull

fun FragmentHostActivity.assertShowsNothing() {
    Espresso.onIdle()
    val routeTextView = findViewById<TextView?>(R.id.routeText)
    val fragmentTextView = findViewById<TextView?>(R.id.fragmentText)
    assertNull(routeTextView)
    assertNull(fragmentTextView)
}