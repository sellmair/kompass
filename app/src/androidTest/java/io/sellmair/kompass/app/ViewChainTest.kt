package io.sellmair.kompass.app

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewChainTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startAtOne_navigateToTwo_navigateToThree_navigateToFour() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)

        Holder.main.navigateTo(Destination.VTwo("two"))
        shows(Target.ViewTwo)

        Holder.main.navigateTo(Destination.VThree("three"))
        shows(Target.ViewThree)

        Holder.main.navigateTo(Destination.VFour("four"))
        shows(Target.ViewFour)
    }

}