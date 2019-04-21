package io.sellmair.kompass.android.test.fragment

import android.widget.TextView
import androidx.test.espresso.Espresso
import io.sellmair.kompass.android.test.FragmentHostActivity
import io.sellmair.kompass.android.test.flatViewHierarchy
import org.junit.Assert

fun FragmentHostActivity.assertShowsRoute(route: BaseRoute) {
    Espresso.onIdle()

    val routeTextView = findViewById<TextView?>(R.id.routeText)
    checkNotNull(routeTextView) { "Expected route $route to be displayed, but no route is present" }
    Assert.assertEquals("Wrong route displayed.", route.text, routeTextView.text)

    val routeTextCount = routeTextView.rootView.flatViewHierarchy().count { view -> view.id == R.id.routeText }
    Assert.assertEquals("More than one route is currently displayed", 1, routeTextCount)

}

