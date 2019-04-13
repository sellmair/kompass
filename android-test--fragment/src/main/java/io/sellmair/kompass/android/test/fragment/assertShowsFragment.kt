package io.sellmair.kompass.android.test.fragment

import android.widget.TextView
import androidx.test.espresso.Espresso
import io.sellmair.kompass.android.test.FragmentHostActivity
import org.junit.Assert


inline fun <reified T : BaseFragment> FragmentHostActivity.assertShowsFragment() {
    Espresso.onIdle()
    val fragmentTextView = findViewById<TextView?>(R.id.fragmentText)
    checkNotNull(fragmentTextView) {
        "Required fragment ${T::class.java.simpleName}, but no fragment is present"
    }

    Assert.assertEquals(
        "Wrong fragment displayed.",
        T::class.text(), fragmentTextView.text
    )
}


