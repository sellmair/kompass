package io.sellmair.kompass.app

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.sellmair.kompass.app.Destination.*
import io.sellmair.kompass.app.Target.FragmentFive
import io.sellmair.kompass.app.Target.FragmentFour
import io.sellmair.kompass.app.Target.FragmentOne
import io.sellmair.kompass.app.Target.FragmentSeven
import io.sellmair.kompass.app.Target.FragmentTen
import io.sellmair.kompass.app.Target.FragmentThree
import io.sellmair.kompass.app.Target.FragmentTwo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FragmentChainTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startOne_navigateToTwo_navigateToThree() {
        Holder.main.startAt(One("startOne"))
        shows(FragmentOne)

        Holder.main.navigateTo(Two("navigateToTwo"))
        shows(FragmentTwo)

        Holder.main.navigateTo(Three("navigateToThree"))
        shows(FragmentThree)
    }

    @Test
    fun startOne_navigateToTwo_navigateToFive_navigateToTen() {
        Holder.main.startAt(One("startOne"))
        shows(FragmentOne)

        Holder.main.navigateTo(Two("navigateToTwo"))
        shows(FragmentTwo)

        Holder.main.navigateTo(Five("navigateToFive"))
        shows(FragmentFive)

        Holder.main.navigateTo(Ten("navigateToTen"))
        shows(FragmentTen)
    }


    @Test
    fun startOne_navigateToTwo_navigateToThree_back_navigateToFour_back() {
        Holder.main.startAt(One("one"))
        shows(FragmentOne)

        Holder.main.navigateTo(Two("two"))
        shows(FragmentTwo)

        Holder.main.navigateTo(Three("three"))
        shows(FragmentThree)

        Holder.main.back()
        shows(FragmentTwo)

        Holder.main.navigateTo(Four("four"))
        shows(FragmentFour)

        Holder.main.back()
        shows(FragmentTwo)
    }


    @Test
    fun startOne_navigateToTwo_beamToThree_back_navigateToFour_back() {
        Holder.main.startAt(One("one"))
        shows(FragmentOne)

        Holder.main.navigateTo(Two("two"))
        shows(FragmentTwo)

        Holder.main.beamTo(Three("three"))
        shows(FragmentThree)

        Holder.main.back()
        shows(FragmentOne)

        Holder.main.navigateTo(Four("four"))
        shows(FragmentFour)

        Holder.main.back()
        shows(FragmentOne)
    }

    @Test
    fun startOne_navigateToFour_startTwo_back_navigateToSeven_back_back_startAtFive_back_back() {
        Holder.main.startAt(One("one"))
        shows(FragmentOne)

        Holder.main.navigateTo(Four("four"))
        shows(FragmentFour)

        Holder.main.startAt(Two("two"))
        shows(FragmentTwo)

        Holder.main.back()
        shows(FragmentTwo)

        Holder.main.navigateTo(Seven("seven"))
        shows(FragmentSeven)

        Holder.main.back()
        shows(FragmentTwo)

        Holder.main.back()
        shows(FragmentTwo)

        Holder.main.startAt(Five("five"))
        shows(FragmentFive)

        Holder.main.back()
        shows(FragmentFive)

        Holder.main.back()
        shows(FragmentFive)
    }
}