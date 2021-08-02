package com.sunday.leetcode

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

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
        nums.sort()
        // [-1,0,1,2,-1,-4]
        run out@{
            nums.forEachIndexed { i, num ->
                val map = mutableMapOf<Int, Int?>()
                if (num > 0) {
                    return@out
                }
                if (i > 0 && num == nums[i - 1]) {
                    return@forEachIndexed
                }
                for (j in i+1 until nums.size) {
                    if (map[-num - nums[j]] != null) {
                        val temp = mutableListOf<Int>()
                        temp.add(nums[i])
                        temp.add(-num - nums[j])
                        temp.add(nums[j])
                        var repeat = true
                        // 去重
                        for (k in 0..2) {
                            if (result.size > 0) {
                                if (result[result.size - 1][k] != temp[k]) {
                                    repeat = false
                                    break
                                }
                            } else {
                                repeat = false
                            }

                        }
                        if (!repeat) {
                            result.add(temp)
                        }
                    }
                    map[nums[j]] = j
                }
            }
        }
        print(result)
        return result
    }

    // 双指针法
    // 还是先排序

    fun threeSum2(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        nums.sort()
        run out@{
            nums.forEachIndexed { i, num ->
                // 如果当前 数字 > 0 后面就不可能存在两个数和
                if (num > 0){
                    return@out
                }
                if (i > 0 && num == nums[i - 1]) {
                    return@forEachIndexed
                }
                var l = i + 1
                var r = nums.size - 1
                while (l < r) {
                    when {
                        // 存在就添加进去
                        num + nums[l] + nums[r] == 0 -> {
                            result.add(listOf(num, nums[l], nums[r]))
                            l ++
                            r --
                            while (l < r && nums[l] == nums[l - 1]) {
                                l ++
                            }
                            while(l < r && nums[r] == nums[r + 1]) {
                                r --
                            }
                        }

                        num + nums[l] + nums[r] < 0 -> {
                            l ++
                        }
                        else -> {
                            r --
                        }
                    }
                }
            }
        }

        print(result)
        return result
    }


    /**
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 使用set
     */
    fun lengthOfLongestSubstring(s: String): Int {
        if (s.length < 2) {
            return s.length
        }
        val set = HashSet<Char>()
        var maxLength = 0
        var i = 0
        while (i < s.length) {
            if (s.length - i < maxLength) break
            set.clear()
            for (j in i until s.length) {
                if (set.contains(s[j])) {
                    maxLength = maxLength.coerceAtLeast(set.size)
                    break
                } else {
                    set.add(s[j])
                }
            }
            // 每次循环结束更新最大值
            maxLength = maxLength.coerceAtLeast(set.size)
            i++
        }
        return maxLength.coerceAtLeast(set.size)
    }

    /**
     * 最长无重复子串解法二
     * 使用HashMap
     * aab
     */
    fun lengthOfLongestSubstring2(s: String): Int {
        if (s.length < 2) {
            return s.length
        }
        //key 是字符 value是下标
        val map = HashMap<Char, Int>()
        var maxLength = 0
        var i = 0
        while (i < s.length) {
            if (s.length - i < maxLength) break
            map.clear()
            for (j in i until s.length) {
                if (map.keys.contains(s[j])) {
                    maxLength = maxLength.coerceAtLeast(map.size)
                    // 更新下次迭代的下标，因为在这之前的子串，一定还会重复
                    i = map[s[j]]!!
                    break
                } else {
                    map[s[j]] = j
                }
            }
            // 每次循环结束更新最大值
            maxLength = maxLength.coerceAtLeast(map.size)
            i++
        }
        return maxLength.coerceAtLeast(map.size)
    }

    /**
     * 二叉树中第二小的节点
     * https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree/
     * 广度优先遍历，
     */

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    /**
     * 解法1广度优先遍历
     */
    fun findSecondMinimumValue(root: TreeNode?): Int {
        if (root?.left == null) return -1
        val list = LinkedList<TreeNode>()
        list.addFirst(root)
        var min = root.`val`
        while (list.isNotEmpty()) {
            val node = list.poll()
            if (node.`val` > root.`val`) {
                min = if (min == root.`val`) {
                    node.`val`
                } else {
                    min.coerceAtMost(node.`val`)
                }
            }
            if (node.`val` == root.`val`) {
                if (node.left != null) {
                    list.addFirst(node.left)
                }
                if (node.right != null) {
                    list.addFirst(node.right)
                }
            }
        }
        return if (min == root.`val`) {
            -1
        } else {
            min
        }

    }


    /**
     * 中序深度遍历
     */
    fun inOrderMid(root: TreeNode?) {
        val stack = Stack<TreeNode>()
        var pNode = root
        if (pNode != null) {
            stack.push(pNode)
        } else {
            return
        }

        while (stack.isNotEmpty()) {
            if (pNode?.left != null) {
                stack.push(pNode.left)
                pNode = pNode.left
            } else{
                val node = stack.pop()
                print(node.`val`)
                if (node.right != null) {
                    pNode = node.right
                    stack.push(pNode)
                }
            }
        }
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2gy9m/
     * 删除排序数组中的重复项
     * 遍历数组，然后清除数组
     * 1,1,2
     * 暴力破解法
     */
    fun removeDuplicates(nums: IntArray): Int {
        var i = 0
        var j : Int
        var length = nums.size
        while (i < length) {
            if (i + 1 < length && nums[i] == nums[i + 1]) {
                j = i
                while (j + 1 < length) {
                    nums[j] = nums[j + 1]
                    j ++
                }
                length --
                i--
            }
            i++
        }
        return length
    }

    /**
     * 双指针法 效率非常高
     * 112
     */
    fun removeDuplicates2(nums: IntArray): Int {
        var left = 0
        var right = 0
        while (right + 1 < nums.size) {
            if (nums[right] != nums[right + 1]) {
                // 将当前的数字放到left后面
                if (right > left) {
                    nums[left + 1] = nums[right + 1]
                }
                left ++
            }
            right ++
        }
        return left + 1
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2zsx1/
     * 买卖股票的最佳时机
     */

    /**
     * 解法1 动态规划
     * 每一天分为两种情况，第一种 手中有股票，第二种手中没有股票
     * 假设第i 天手中有股票 hold, 没股票为noHold
     * 那第i天没股票 分两种，这天没有买卖股票、这天卖了股票
     * noHold = max(noHold, hold + price[i])
     *
     * 那第i天有股票也分两种，这天没有买卖股票、这天买了股票
     * hold = max(hold, noHold - price[i])
     */

    open fun maxProfit(prices: IntArray?): Int {
        if (prices == null || prices.size < 2) return 0
        val length = prices.size
        //初始条件
        var hold = -prices[0] //持有股票
        var noHold = 0 //没持有股票
        for (i in 1 until length) {
            //递推公式转化的
            noHold = noHold.coerceAtLeast(hold + prices[i])
            hold = hold.coerceAtLeast(noHold - prices[i])
        }
        //最后一天肯定是手里没有股票的时候利润才会最大，
        //所以这里返回的是noHold
        return noHold
    }

    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2skh7/
     * 旋转数组
     * 解法1 开辟新数组，将计算好位置的元素放到新数组中
     * 解法2 先全部翻转数组，再翻转前k个，再翻转后length-k个
     */


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x248f5/
     * 是否存在重复数字  set 法
     */
    fun containsDuplicate(nums: IntArray): Boolean {
        val set = HashSet<Int>()
        nums.forEach {
            if (!set.add(it)) {
                return true
            }
        }
        return false
    }

    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x21ib6/
     * 只出现一次的数字
     */

    fun singleNumber(nums: IntArray): Int {
        var result = 0
        nums.forEach {
            result = it.xor(result)
        }
        return result
    }

}