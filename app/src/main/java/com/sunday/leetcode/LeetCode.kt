package com.sunday.leetcode

/**
 * Created by Sunday on 2021/7/15
 */
class Solution {

    //https://leetcode-cn.com/leetbook/read/tencent/xxqfy5/
    /**
     * 两数之和
     * 暴力法
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val result = IntArray(2)
        for (i in 0 until (nums.size - 1)) {
            for (j in i+1..nums.size) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i
                    result[1] = j
                    return result
                }
            }
        }
        return result
    }

    /**
     * 表查找法
     * 一定有 target - nums[i] = nums[j]
     * 即map[target - nums[i]] != null num[j] 已经在没找到的时候赋值了
     */
    fun twoSum2(nums: IntArray, target: Int): IntArray {
        // key存值，value存下标
        val map = HashMap<Int, Int>(nums.size - 1)
        for (i in nums.indices) {
            //比如 31245
            // target = 6
            // 当map[6 - 3] = map[3] == null map[3] = 0
            // map[6 - 1] = map[5] == null map[1] = 1
            // map[6 - 2] = map[4] == null map[2] = 2
            // map[6 - 4] = map[2] != null map[4] = 3
            // map[6 - 5] = map[1] ==
            // map[target - nums[n]] != null = nums[m], 说明存在 nums[m] + num[n] = target
           if (map[target - nums[i]] != null) {
               return IntArray(2).apply {
                   this[0] = map[target - nums[i]]!!
                   this[1] = i

               }
           }
            map[nums[i]] = i
        }
        // 3 2 4
        // m[ 6 - 3] = m[3]   m[4] = 0;
        // m[6 - 2] = m[4]    m[2] = 1
        // m[6 - 4] = m[2]
        return IntArray(2)
    }



    //https://leetcode-cn.com/leetbook/read/tencent/xx6c46/
    /**
     * 寻找两个有序数组的中位数
     * 合并有序数组
     */

    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val length1 = nums1.size
        val length2 = nums2.size
        val result = IntArray(length1 + length2)

        if (nums1.isEmpty() && nums2.isEmpty()) {
            return 0.0
        }
        var i = 0
        var j = 0
        var k = 0
        while (i < length1 && j < length2) {
            if (nums1[i] < nums2[j]) {
                result[k++] = nums1[i++]
            } else {
               result[k++] = nums2[j++]
            }
        }
        while (i < length1) {
            result[k++] = nums1[i++]
        }
        while (j < length2) {
            result[k++] = nums2[j++]
        }
        return if (result.size % 2 != 0) {
            result[result.size / 2].toDouble()
        } else {
            (result[result.size / 2 - 1] + result[result.size / 2]) / 2.0
        }
    }


    /**
     * 最长回文子串
     * https://leetcode-cn.com/leetbook/read/tencent/xxk4s2/
     * 中心扩散法
     */

    fun longestPalindrome(s: String): String {
        var start = 0
        var maxLength = 0
        if (s.length < 2) {
            return s
        }
        
        val chars = s.toCharArray()
        val length = chars.size

        var i = 0
        while (i < length) {
            // 如果剩余长度和小于等于最长子串，就没必要再比较了
            if ((length - i) <= maxLength / 2) {
                break
            }

            var left = i
            var right = i
            // 第一步 向右寻找相同的字符串
            while (right < length - 1 && chars[right] == chars[right + 1]) {
                // 继续向右寻找
                right ++
            }
            var goNext = false
            while (right < length - 1 && left > 0 && chars[right + 1] == chars[left - 1]) {
                right++
                left--
                goNext = true
            }
            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1
                start = left
            }
            if (!goNext) {
                i = right + 1
            } else{
                i++
            }
        }
        return s.substring(start, start + maxLength)
    }

    /**
     * 寻找最长公共前缀
     * https://leetcode-cn.com/leetbook/read/tencent/xxzqki/
     * 提示 ： 如果有最长公共前缀，肯定也是第一个字符的一部分
     * 第一步 假设 最长公共前缀是第一个 先和第二个比较，如果第一个不是第二个的前缀，则截取第一个字符串，继续比较，
     * 如果得到了1和2的公共前缀，则将公共前缀更新，继续比第三个、第四个
     * 横向扫描法
     * 纵向扫描法
     * 分治法
     * 二分查找法
     *
     */

    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) {
            return ""
        }
        var i = 1
        var pre = strs[0]
        val length = strs.size
        while (i < length) {
            while (strs[i].indexOf(pre) != 0) {
                // 将假设最小前缀 - 1
                pre = pre.substring(0, pre.length - 1)
            }
            // 没找到最小前缀
            if (pre.isBlank()) {
                break
            }
            // 找到了前面的最小前缀
            i ++
        }
        return pre
    }


    /**
     * 三数之和
     * https://leetcode-cn.com/leetbook/read/tencent/xxst6e/
     * a + b + c = 0
     * 解法1 转化为两数之和
     */

    fun threeSum(nums: IntArray): List<List<Int>> {

        val result = mutableListOf<List<Int>>()
        val temp = mutableListOf<Int>()
        val map = mutableMapOf<Int, Int?>()
        nums.forEachIndexed { i, num ->
            map.clear()
            temp.clear()
            for (j in i+1 until nums.size) {
                if (map[-num - nums[j]] != null) {
                    temp.add(i)
                    temp.add(j)
                    map[num - nums[j]]?.let { temp.add(it) }
                }
                map[nums[j]] = j
            }
        }
        return result
    }
}