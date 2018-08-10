package io.sellmair.kompass

import android.support.test.runner.AndroidJUnit4
import android.test.UiThreadTest
import io.sellmair.kompass.internal.BackStackImpl
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RunWith(AndroidJUnit4::class)
class BackStackImplTest {
    private lateinit var backStackImpl: BackStackImpl

    @Before
    fun setup() {
        backStackImpl = BackStackImpl()
    }


    @Test
    fun back_performsLastAction() {

        val lock = ReentrantLock()
        val condition = lock.newCondition()

        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }

        var called = false

        backStackImpl.onBack {
            called = true

            lock.withLock {
                condition.signalAll()
            }

        }

        lock.withLock {
            backStackImpl.back()
            condition.await(1, TimeUnit.MINUTES)
        }

        Assert.assertTrue(called)
    }


    @Test
    @UiThreadTest
    fun backImmediate_performsLastAction() {
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }

        var called = false
        backStackImpl.onBack { called = true }

        backStackImpl.backImmediate()

        Assert.assertTrue(called)
    }


    @Test
    @UiThreadTest
    fun backImmediate_performsLastAction_withGivenKey() {
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        var called = false
        backStackImpl.onBack("right") { called = true }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }

        backStackImpl.backImmediate("right")

        Assert.assertTrue(called)
    }


    @Test
    @UiThreadTest
    fun backImmediate_performsLastTwoAction_withGivenKey() {
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }

        var calledSecond = false
        backStackImpl.onBack("right") { calledSecond = true }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }

        var calledFirst = false
        backStackImpl.onBack("right") { calledFirst = true }
        backStackImpl.onBack("wrong") { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }
        backStackImpl.onBack { Assert.fail("wrong action performed") }

        backStackImpl.backImmediate("right")
        Assert.assertTrue(calledFirst)
        Assert.assertFalse(calledSecond)

        backStackImpl.backImmediate("right")
        Assert.assertTrue(calledFirst)
        Assert.assertTrue(calledSecond)
    }


    @Test
    @UiThreadTest
    fun remove() {
        var stayed1 = false
        backStackImpl.onBack("stay") { stayed1 = true }

        var stayed2 = false
        backStackImpl.onBack("stay") { stayed2 = true }

        backStackImpl.onBack("remove") { fail("action not removed") }

        var stayed3 = false
        backStackImpl.onBack { stayed3 = true }

        backStackImpl.onBack("remove") { fail("action not removed") }



        backStackImpl.remove("remove")

        do {
            val popped = backStackImpl.backImmediate()
        } while (popped)



        Assert.assertTrue(stayed1)
        Assert.assertTrue(stayed2)
        Assert.assertTrue(stayed3)
    }

    @Test
    @UiThreadTest
    fun onBack_withKeySingle() {
        backStackImpl.onBack { fail("wrong action performed") }
        backStackImpl.onBack { fail("wrong action performed") }
        backStackImpl.onBack("myKey") { fail("wrong action performed") }
        var called2 = false
        backStackImpl.onBack("myOtherKey", keySingle = true) { called2 = true }
        backStackImpl.onBack("myKey", keySingle = true) { fail("wrong action performed") }
        var called1 = false
        backStackImpl.onBack("myKey", keySingle = true) { called1 = true }

        backStackImpl.backImmediate()
        Assert.assertTrue(called1)
        Assert.assertFalse(called2)

        backStackImpl.backImmediate()
        Assert.assertTrue(called1)
        Assert.assertTrue(called2)

    }
}