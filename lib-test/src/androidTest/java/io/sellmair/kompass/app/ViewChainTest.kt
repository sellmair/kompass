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

    @Test
    fun startAtOne_navigateToTwo_beamToThree_back() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)

        Holder.main.navigateTo(Destination.VTwo("two"))
        shows(Target.ViewTwo)

        Holder.main.beamTo(Destination.VThree("three"))
        shows(Target.ViewThree)

        Holder.main.back()
        shows(Target.ViewOne)
    }


    @Test
    fun startAtOne_startAtTwo_back_navigateToThree_navigateToFour_back_back_back() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)

        Holder.main.startAt(Destination.VTwo("two"))
        shows(Target.ViewTwo)

        Holder.main.back()
        shows(Target.ViewTwo)

        Holder.main.navigateTo(Destination.VThree("three"))
        shows(Target.ViewThree)

        Holder.main.navigateTo(Destination.VFour("four"))
        shows(Target.ViewFour)

        Holder.main.back()
        shows(Target.ViewThree)

        Holder.main.back()
        shows(Target.ViewTwo)

        Holder.main.back()
        shows(Target.ViewTwo)
    }


    @Test
    fun startAtOne_beamToTwo_beamToThree_beamToFour_back() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)

        Holder.main.beamTo(Destination.VTwo("two"))
        shows(Target.ViewTwo)

        Holder.main.beamTo(Destination.VThree("three"))
        shows(Target.ViewThree)

        Holder.main.beamTo(Destination.VFour("four"))
        shows(Target.ViewFour)

        /*
        We expect ViewFour instead of ViewOne, because
        beamTo replaced current screen (which is ViewOne)
         */
        //Holder.main.back()
        //shows(Target.ViewFour)
    }


    @Test
    fun startAtOne_navigateFive__beamToTwo_beamToThree_beamToFour_back() {
        Holder.main.startAt(Destination.VOne("one"))
        shows(Target.ViewOne)

        Holder.main.navigateTo(Destination.VFive("five"))
        shows(Target.ViewFive)

        Holder.main.beamTo(Destination.VTwo("two"))
        shows(Target.ViewTwo)

        Holder.main.beamTo(Destination.VThree("three"))
        shows(Target.ViewThree)

        Holder.main.beamTo(Destination.VFour("four"))
        shows(Target.ViewFour)

        Holder.main.back()
        shows(Target.ViewOne)
    }

}