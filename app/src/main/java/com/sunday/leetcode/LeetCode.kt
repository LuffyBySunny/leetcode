package com.sunday.leetcode

/**
 * Created by Sunday on 2021/7/15
 */
class Solution {
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
        for (i in 0 until nums.size) {
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
}