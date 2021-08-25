package com.sunday.leetcode

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val solution = Solution()

        var a = 10
        var b = a
        a = 20
        println(solution.maxLengthChar("aaabbbcbbccc"))
        println(b)
    }
}
