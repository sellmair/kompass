package io.sellmair.kompass.app

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewSingleOperatorTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startAt() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)
    }

    @Test
    fun navigateTo() {
        Holder.main.navigateTo(Destination.VTwo("two"))
        shows(Target.ViewTwo)
    }

    @Test
    fun beamTo() {
        Holder.main.navigateTo(Destination.VThree("three"))
        shows(Target.ViewThree)
    }
}
