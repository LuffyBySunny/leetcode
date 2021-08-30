package com.sunday.leetcode

import android.os.Handler
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val test = com.sunday.leetcode.Test()

        test.outPut("123", "abc")


        Thread.sleep(10000)

        var concurrentHashMap = ConcurrentHashMap<Int, Int>()
        concurrentHashMap.size

        val atomicInteger = AtomicInteger(0)
        atomicInteger.compareAndSet(0, 1)

    }



    companion object {
        @JvmStatic
        fun main(args : Array<String>) : Unit {

        }
    }


}
