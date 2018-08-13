package io.sellmair.kompass.app

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FragmentSingleOperatorTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun startAt() {
        val key = "startAt"
        Holder.main.startAt(Destination.One(key))
        shows(Target.FragmentOne)
    }

    @Test
    fun navigateTo() {
        val key = "startAt"
        Holder.main.navigateTo(Destination.One(key))
        shows(Target.FragmentOne)

    }

    @Test
    fun beamTo() {
        val key = "startAt"
        Holder.main.beamTo(Destination.One(key))
        shows(Target.FragmentOne)
    }
}