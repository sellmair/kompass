package io.sellmair.kompass.app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers

fun shows(id: Target) {
    onView(ViewMatchers.withId(R.id.target_id))
        .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        .check(ViewAssertions.matches(ViewMatchers.withText(id.name)))
}