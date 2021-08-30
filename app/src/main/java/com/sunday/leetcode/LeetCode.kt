package com.sunday.leetcode

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.max
import java.util.Scanner
import kotlin.collections.ArrayList


/**
 * Created by Sunday on 2021/7/15
 * https://leetcode-cn.com/problem-list/2cktkvj/ 100 题
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
           if (map[target - nums[i]] != null) {
               return IntArray(2).apply {
                   this[0] = map[target - nums[i]]!!
                   this[1] = i

               }
           }
            map[nums[i]] = i
        }
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
     * 外层循环 i 从i 开始扩散
     * 右指针right 不断向右寻找一个不重复的字符
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
            // 如果剩余长度小于等于最长子串，就没必要再比较了
            if ((length - i) <= maxLength / 2) {
                break
            }

            var left = i
            var right = i
            // 第一步 向右寻找相同的字符串，左指针不变
            while (right < length - 1 && chars[right] == chars[right + 1]) {
                // 继续向右寻找
                right ++
            }
            while (right < length - 1 && left > 0 && chars[right + 1] == chars[left - 1]) {
                right++
                left--
            }
            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1
                start = left //更新开始下标
            }

            i++

        }
        return s.substring(start, start + maxLength)
    }

    /**
     * 寻找最长公共前缀
     * https://leetcode-cn.com/leetbook/read/tencent/xxzqki/
     * 提示 ： 如果有最长公共前缀，肯定也是第一个字符的一部分
     * 第一步 假设 最长公共前缀是第一个 先和第二个比较，如果第一个不是第二个的前缀，则截取第一个字符串，继续比较，
     * 如果得到了1和2的公共前缀，则将公共前缀更新，继续比第三个、第四个
     * 纵向扫描法
     *
     */

    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) {
            return ""
        }
        var i = 1
        var pre = strs[0] // 假设最小前缀是第一个
        val length = strs.size
        while (i < length) {

            //不断的找到当前的最小公共前缀
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


    /**
     * 三数之和，双指针法
     * 先排序
     * 以当前元素为和，向后面寻找两个数相加和当前数相等
     */

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
                // 找出所有情况
                while (l < r) {
                    when {
                        // 存在就添加进去
                        num + nums[l] + nums[r] == 0 -> {
                            result.add(listOf(num, nums[l], nums[r]))
                            l ++
                            r --

                            // 去重
                            // 左边相等的话就右移左指针
                            while (l < r && nums[l] == nums[l - 1]) {
                                l ++
                            }
                            //右边相等就右指针左移
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
        return result
    }


    /**
     * 最长无重复子串
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 使用set
     */
    fun lengthOfLongestSubstring(s: String): Int {
        if (s.length < 2) {
            return s.length
        }
        val set = HashSet<Char>()
        var maxLength = 0
        // 如果需要保存最大长度的String时 还需将最大时的 left指针个right指针保存
        var i = 0
        while (i < s.length) {
            if (s.length - i < maxLength) break
            set.clear()
            for (j in i until s.length) {
                if (set.contains(s[j])) {
                    // 如果存在相同的就跳出循环，左 指针 + 1
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
     * 使用HashMap，保存每个字符的位置，找到重复子串之后
     * aab
     */
    fun lengthOfLongestSubstring2(s: String): Int {
        if (s.length < 2) {
            return s.length
        }
        //key 是字符 value是下标
        val map = HashMap<Char, Int>() // 保存不重复的字符
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
        var min = root.`val` //
        while (list.isNotEmpty()) {
            val node = list.poll()
            // 根是最小节点
            if (node.`val` > root.`val`) {
                min = if (min == root.`val`) {
                    node.`val`
                } else {
                    min.coerceAtMost(node.`val`)
                }
            }
            // 只有该节点等于
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
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2gy9m/
     * 删除排序数组中的重复项
     * 遍历数组，然后移动数组
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
     * 删除排序数组中的重复项
     * 双指针法 左指针是不重复项的结尾，right用来寻找下一个不重复项，将不重复项移到左指针后面的位置
     * 112
     */
    fun removeDuplicates2(nums: IntArray): Int {
        var left = 0
        var right = 0
        while (right + 1 < nums.size) {
            if (nums[right] != nums[right + 1]) {
                // 找到一个不重复的数字之后
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

     fun maxProfit(prices: IntArray?): Int {
        if (prices == null || prices.size < 2) return 0
        val length = prices.size
        //初始条件
        var hold = -prices[0] //持有股票
        var noHold = 0 //没持有股票
        for (i in 1 until length) {
            //递推公式转化的
                //没持有股票的最大值
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
     * 解法3 原地旋转数组
     */

    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnhhkv/
     * 旋转二维数组
     * 之前很怕这个题啊
     * 直接旋转
     */

    fun rotate(matrix: Array<IntArray>): Unit {
        val size = matrix.size

        // 对称的，只需要旋转上半部分
        // 由左下角向右转
        for (i in 0 until size / 2) {
            for (j in i until size - i - 1) {

                val m = size - j - 1 // 行号

                val n = size - i - 1 // 列号
                // 左上角元素
                // matrix[i][j] 左上
                // matrix[m][i] 左下
                // matrix[n][m] 右下
                // matrix[j][n]
                val temp = matrix[i][j]
                matrix[i][j] = matrix[m][i]
                matrix[m][i] = matrix[n][m]
                matrix[n][m] = matrix[j][n]
                matrix[j][n] = temp
            }
        }
    }





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
     * 只出现一次的数字，所有结果异或 得到的就是不重复的
     */

    fun singleNumber(nums: IntArray): Int {
        var result = 0
        nums.forEach {
            result = it.xor(result)
        }
        return result
    }


    /**
     * https://leetcode-cn.com/problems/UHnkqh/
     * 链表翻转
     */

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }
    //[1,2,3,4,5]
    fun reverseList(head: ListNode?): ListNode? {
        var left : ListNode? = null // 左节点
        var right = head // 头结点
        while (right != null) {
            val temp = right.next
            right.next = left
            left = right // 左节点右移
            right = temp // 头结点右移

        }
        return left
    }


    /**
     * 整数翻转
     * https://leetcode-cn.com/problems/reverse-integer/
     * 有最大值和最小值 以及符号
     * 321
     */

    fun reverse(x: Int): Int {
        val fuhao = if (x > 0) 1 else -1
        var source = Math.abs(x)
        var result = 0
        while (source > 0) {
            val i = source % 10
            source /= 10
            // 如果大于最大值
            if (result > (Int.MAX_VALUE - i) / 10) {
                return 0
            }
            result = result * 10 + i
        }
        return result * fuhao
    }


    /**
     * https://leetcode-cn.com/problems/palindrome-number/
     * 是否是回文数字
     * 将数字翻转，直到结果 >= source，或者翻转完毕
     */

    fun isPalindrome(x: Int): Boolean {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false
        var source = x
        var result = 0
        while (source > 0) {
            val i = source % 10
            source /= 10
            // 如果大于最大值
            if (result > (Int.MAX_VALUE - i) / 10) {
                return false
            }
            result = result * 10 + i

            // 如果结果大于剩余数字就没有再比较下去的必要了
            if (result >= source) {
                break
            }
        }
        return (source == result) or (result / 10 == source)
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2y0c2/
     * 两个数组的交集
     * 双指针法 先排序
     * 1,2,2,1
     * 2,2
     */

    fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
        nums1.sort()
        nums2.sort()
        val result = mutableListOf<Int>()
        var i = 0
        var j = 0
        while (i < nums1.size && j < nums2.size) {
            if (nums1[i] == nums2[j]) {
                result.add(nums1[i])
                i++
                j++
                continue
            }
            // 如果不相同，小的一方移动
            if (nums1[i] < nums2[j]) {
                i ++
            } else {
                j ++
            }
        }
       return result.toIntArray()
    }

    /**
     * 解法2 使用map记录每个元素出现的个数
     * 这个方法比第一个好多了
     * 注意 记录相关的思想就用map
     */
    fun intersect2(nums1: IntArray, nums2: IntArray): IntArray {

        val result = mutableListOf<Int>()
        val nums1Map = HashMap<Int, Int>(5)
        // 先把每个数字出现的个数保存下来
        nums1.forEach {
            if (nums1Map.contains(it)) {
                nums1Map[it] = nums1Map[it]!!.plus(1)
            } else {
                nums1Map[it] = 1
            }
        }
        // 如果该数组包含的话就直接添加，同时将个数减一，方式重复添加
        nums2.forEach {
            if (nums1Map.contains(it) && nums1Map[it]!! > 0) {
                result.add(it)
                nums1Map[it] = nums1Map[it]!! - 1
            }
        }
        return result.toIntArray()
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2cv1c/
     * 数组加1
     * 8,9,9,9
     */
    fun plusOne(digits: IntArray): IntArray {
        var carrier = 0 // 进位
        val result = mutableListOf<Int>()
        digits.forEach {
            result.add(it)
        }
        val size = result.size
        for (i in size - 1 downTo 0) {
            var fix = 0 // 最后一位 + 1 前面的都不会加 1
            if (i == size - 1) {
                fix = 1
            }
            if (digits[i] + fix + carrier < 10) {
                result[i] = digits[i] + fix + carrier
                return result.toIntArray()
            } else {
                result[i] = 0
                if (i == 0) {
                    result.add(0, 1)
                }
                carrier = 1
            }
        }
        return result.toIntArray()
    }

    /**
     *
     */
    fun plusOne2(digits: IntArray): IntArray {
        for (i in digits.size - 1 downTo 0) {
            // 如果这个数字不是9 则直接++返回
            if (digits[i] != 9) {
                digits[i] ++
                return digits
            } else{
                digits[i] = 0
            }
        }
        // 走到这说明全是9
        val result = IntArray(digits.size + 1)
        result[0] = 1
        return result
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2ba4i/
     * 移动0
     * 暴力法
     */
    fun moveZeroes(nums: IntArray): Unit {
        var left = 0
        var right = nums.size - 1
        while (left < right) {
            if (nums[left] != 0) {
                left++
                continue
            }
            //将数组向前移动一位
            var i = left
            while (i < right) {
                nums[i] = nums[i + 1]
                i++
            }
            // 将末尾数组置为0
            nums[right] = 0
            // 右指针向前一位
            right--
        }
    }


    /**
     * 换一种思路
     * 将非0的向前方，后面的补0
     */
    fun moveZeroes2(nums: IntArray): Unit {
        var index = 0 // 上个非0值的位置
        // 先把不为0的数字全部放前面
        nums.forEach {
            if (it != 0) {
                nums[index] = it
                index ++
            }
        }

        // 再往后面补0
        while (index < nums.size) {
            nums[index] = 0
            index ++
        }
    }

    /**
     * 双指针
     */
    fun moveZeroes3(nums: IntArray): Unit {
        var left = 0 // 不为0的指针
        var right = 0
        if(nums.size < 2) return
        while (right < nums.size) {
           if (nums[right] != 0) {
               if (right > left) {
                   nums[left] = nums[right]
                   nums[right] = 0
               }

               left ++
           }
            right++
        }
    }

    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2f9gg/
     * 有效的数独
     * 将每一个单元存在的数字存下来，遍历的时候，如果再遍历到，则视为非数独
     * 这里的每个单元指的是 行、列、小9宫格
     */

    fun isValidSudoku(board: Array<CharArray>): Boolean {
        // 先定义三个存储单元
        val size = board.size
        // [0][3] 表示第 0行是否有数字3
        // 也可以用HashMap
        val row = Array(size){IntArray(size)}
        val column = Array(size){IntArray(size)}
        val set = Array(size){IntArray(size)}
        for (i in board.indices) {
            for (j in board[0].indices) {
                // 先把数字拿出来
                if (board[i][j] == '.') {
                    continue
                }
                // 算出数字
                val num = board[i][j] - '0' - 1
                //如果第i行包含这个数字 返回 false
                if (row[i][num] != 0) {
                    return false
                }

                // 第J列
                if (column[j][num] != 0) {
                    return false
                }
                //计算出是第几个九宫格
                val index = i / 3 * 3 + j / 3
                if (set[index][num] != 0) {
                    return false
                }
                row[i][num] = 1
                column[j][num] = 1
                set[index][num] = 1
            }
        }
        return true
    }



    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnhbqj/
     * 字符串翻转
     * 方法一 递归
     */
    fun reverseString(s: CharArray): Unit {
        reverse(0, s.size - 1, s)
    }

    fun reverse(left: Int, right: Int, s: CharArray) {
        if (left > right) return
        val temp = s[left]
        s[left] = s[right]
        s[right] = temp
        reverse(left + 1, right - 1, s)
    }

    /**
     * 方法二 双指针套循环
     */

    fun reverseString2(s: CharArray): Unit {
        var left = 0
        var right = s.size - 1

        while (left < right) {
            val temp = s[left]
            s[left] = s[right]
            s[right] = temp
            left ++
            right --
        }
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn5z8r/
     * 字符串中的第一个唯一字符
     */

    fun firstUniqChar(s: String): Int {
        val set = HashMap<Char, Int>()
        var i = 0
        while (i < s.length) {
            set[s[i]] = set[s[i]]?.plus(1) ?: 1
            i++
        }
        i = 0
        while (i < s.length) {
            if (set[s[i]] == 1) {
                return i
            }
            i++
        }

        return -1
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn96us/
     * 字母异位词
     * 两个字符串中的 字母是否完全相同
     * 使用Hash表存储第一个字符串
     */

    fun isAnagram(s: String, t: String): Boolean {

        var i = 0
        val array = IntArray(26)


        // 保存第一个字符串每个字母的个数
        while (i < s.length) {
            array[s[i++] - 'a']++
        }

        i = 0
        // 如果有就减一
        while (i < t.length) {
            array[t[i++] - 'a']--
        }

        array.forEach {
            if (it != 0) {
                return false
            }
        }

        return true
    }


    /**
     * 实现Indexof KPM算法
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnr003/
     */

    fun strStr(haystack: String, needle: String): Int {
        return haystack.indexOf(needle)
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xne8id/
     * 验证回文字符串
     * 双指针
     */

    fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1
        if (s.length < 2) return true

        while (left < right) {
            // 跳过特殊字符
            while (left < right && !Character.isLetterOrDigit(s[left])) {
                left ++
            }
            while (left < right && !Character.isLetterOrDigit(s[right])) {
                right --
            }
            if (s[left].equals(s[right], true)) {
                left++
                right--
            } else {
                return false
            }
        }
        return true
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnpvdm/
     * 外观数列
     * 递归解法
     *
     */

    fun countAndSay(n: Int): String {
        if(n == 1) return "1"
        // 每次计算前都能获取上次的结果
        // 获得上次的结果
        val last = countAndSay(n - 1)

        // 统计每个字符的个数
        var count = 0
        // 当前的字符
        var current = last[0]
        val result = StringBuilder()
        // 对当前字符串进行遍历
        last.forEach {
            if (it == current) {
                // 相等的时候只需记录个数
                count ++
            } else {
                // 不相等的时候保存结果，然后更新个数和需要统计的字符
                result.append(count)
                result.append(current)
                count = 1
                current = it
            }
        }
        // 保存最后的结果
        result.append(count)
        result.append(current)
        return result.toString()
    }



    /**链表*/

    /**
     * 删除链表中的节点
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnarn7/
     * 只输入要删除的节点
     * 解法：将下一个节点的值拿过来，然后指向下个节点的下个
     */
    fun deleteNode(node: ListNode?) {
        node!!.`val` = node.next!!.`val`
        node.next = node.next!!.next
    }

    /**
     * 删除链表的倒数第n个结点
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn2925/
     * 双指针解法，right 比 left快n个，当right.next为null的时候删除 left就行了
     */

    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var left = head // 代表倒数n + 1 个结点，删除的是它的下一个节点
        var right = head
        var count = 0
        while (right != null) {
            if (count > n) {
                left = left?.next
            }
            right = right.next
            count ++
        }

        // 当删除的是第一个节点需要特殊处理
        if (n == count) {
            return head?.next
        }

        left!!.next = left.next?.next

        return head
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnnbp2/
     * 合并两个有序链表
     */

    // 双指针，谁大谁往后
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null)
            return l2
        if (l2 == null)
            return l1
        val dummy = ListNode(0)
        var  curr = dummy
        var one  = l1
        var two = l2

        while (one != null && two != null) {
            //比较一下，哪个小就把哪个放到新的链表中
            if (one.`val` <= two.`val`) {
                curr.next = one
                one = one.next
            } else {
                curr.next = two
                two = two.next
            }
            curr = curr.next!!
        }
        //然后把那个不为空的链表挂到新的链表中
        curr.next = one ?: two
        return dummy.next
    }

    /**
     * 两个栈实现一个队列
     * https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
     */

    val stack1 = Stack<Int>() // 存放插入数据
    val stack2 = Stack<Int>() // 存放要pop出去的数据
    fun appendTail(value: Int) {
        stack1.push(value)
    }

    fun deleteHead(): Int {
        var result = -1
        // 如果需要pop的栈没数据，先把stack1的数据填充上去
        if (stack2.isEmpty()) {
            while (stack1.isNotEmpty()) {
                stack2.push(stack1.pop())
            }
        }
        if (stack2.isNotEmpty()) {
            result = stack2.pop()
        }

        return result
    }


    /**
     * https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/
     * 包含最小值的栈
     */
    var dataStack = Stack<Int>()
    var minStack = Stack<Int>() // 存贮当前最小值
    fun push(x: Int) {
        dataStack.push(x)
        if (minStack.isEmpty()) {
            minStack.push(x)
        } else {
            if (minStack.peek() < x) {
                minStack.push(minStack.peek())
            } else {
                minStack.push(x)
            }
        }
    }

    fun pop() {
        dataStack.pop()
        minStack.pop()
    }

    fun top(): Int {
        return dataStack.peek()
    }

    fun min(): Int {
        return minStack.peek()
    }


    /**
     * https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/
     * 从尾到头打印链表
     * 递归，相当于链表翻转
     */
    val array = mutableListOf<Int>()
    fun reversePrint(head: ListNode?): IntArray {
        if (head?.next == null) {
            if (head != null) {
                array.add(head.`val`)
            }
            return array.toIntArray()
        }
        reversePrint(head.next)
        array.add(head.`val`)
        return array.toIntArray()
    }

    /**
     * 解法2 使用栈，正好是倒序
     */
    fun reversePrint2(head: ListNode?): IntArray {
        var next = head
        val stack = LinkedList<Int>()
        while (next != null) {
            stack.addLast(next.`val`)
            next = next.next
        }
        array.clear()
        while (stack.isNotEmpty()) {
            array.add(stack.removeLast())
        }
        return array.toIntArray()
    }


    /**
     * 再次翻转链表
     */
    fun reverseList3(head: ListNode?): ListNode? {
        var prev : ListNode? = null
        var next = head
        while (next != null) {
            val temp = next.next
            next.next = prev
            prev = next
            next = temp
        }
        return prev
    }

    /**
     * 复杂链表的复制
     * https://leetcode-cn.com/problems/fu-za-lian-biao-de-fu-zhi-lcof/
     * 复制本身和random指针
     */

    class Node(val `val`: Int) {
        var next: Node?
        var random: Node?
        var children: List<Node?> = listOf()
        init {
            next = null
            random = null
        }
    }
    fun copyRandomList(head: Node?): Node? {
        val map = mutableMapOf<Node?, Node?>()
        var curr = head
        // 复制节点，完成映射，key为老节点，value为新节点
        while (curr != null) {
            map[curr] = Node(curr.`val`)
            curr = curr.next
        }

        curr = head
        // 完成指针的
        while (curr != null) {
            // 每个value都是新创建的值
            map[curr]?.next = map[curr.next]
            map[curr]?.random = map[curr.random] //  map[curr.random] 在上一步已经被完美的复制了
            curr = curr.next
        }
        return map[head]
    }


    /**
     * https://leetcode-cn.com/problems/valid-parentheses/
     * 有效的括号
     * 使用栈，遇到右括号就将左括号出栈，如果不匹配直接返回false
     */
    fun isValid(s: String): Boolean {
        val stack = Stack<Char>()
        s.forEach {
            if (it == ')') {
                if (stack.isEmpty()) {
                    return false
                }
                if (stack.pop() != '(') {
                    return false
                }
            } else if (it == ']') {
                if (stack.isEmpty()) {
                    return false
                }
                if (stack.pop() != '[') {
                    return false
                }
            } else if (it == '}') {
                if (stack.isEmpty()) {
                    return false
                }
                if (stack.pop() != '{') {
                    return false
                }
            } else {
                stack.push(it)
            }
        }
        return stack.isEmpty()
    }


    /**
     * https://leetcode-cn.com/problems/container-with-most-water/
     * 盛水最多的容器，双指针
     */

    fun maxArea(height: IntArray): Int {
        var max = 0
        var left = 0
        var right = height.size - 1
        while (left < right) {
            val height1 = Math.min(height[left], height[right]) // 最小高度
            max = Math.max(max, height1 * (right - left))
            // 不断移动最小高度
            if (height[left] < height[right]) {
                left ++
            } else {
                right --
            }
        }
        return max
    }


    /**
     * https://leetcode-cn.com/problems/generate-parentheses/
     * 生成合法的括号
     */

    // 第一步 首先看 怎么生成所有的组合
    val ans = ArrayList<String>()

    /**
     * @param level 层数
     * @param str 每层的字符结果
     * @param left 剩余的左括号
     */
    fun recursion(left: Int, right: Int, level : Int, str : String) {
        // 递归到最后一层返回
        if (level == 0) {
            println(str)
            ans.add(str) //将结果返回
            return
        }
        // 只要还有左括号就说明还能放
        if (left > 0) {
            recursion(left - 1, right, level - 1, str.plus("("))
        }
        // 只有剩余的右括号数量大于剩余的左括号时才能放
        if (left < right) {
            recursion(left, right - 1, level - 1, str.plus(")"))
        }
    }

    fun generateParenthesis(n: Int): List<String> {
        recursion(n, n, n * 2, "")
        return ans
    }


    /**
     * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
     * 多叉树的遍历，和View树的遍历一样
     */

    /**
     * N叉树广度优先搜索
     */
    fun levelOrder(root: Node?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val queue = LinkedList<Node>()
        if (root == null) {
            return emptyList()
        }
        queue.add(root)
        while (queue.isNotEmpty()) {
            val list = mutableListOf<Int>()
            val size = queue.size
            for (i in 0 until size) {
                val node = queue.poll()
                list.add(node.`val`)
                node.children.filterNotNull().forEach {
                    queue.add(it)
                }
            }
            result.add(list)
        }
        return result
    }

    /**
     * 解法2 递归
     * 拿到每一层的list添加
     */

    val results = mutableListOf<MutableList<Int>>()
    fun tranverseNode(node : Node, level : Int) {
        // 递归回来size会比较大，所以判断一下
        if (results.size < level) {
            results.add(mutableListOf())
        }
        results[level].add(node.`val`)
        node.children.filterNotNull().forEach {
            tranverseNode(it, level + 1)
        }
    }


    /**
     * 前序深度遍历 根 左 右
     * 第一种做法 非递归
     */
    fun preorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack  = LinkedList<TreeNode>()
        if (root == null) return emptyList()
        stack.push(root)
        while (stack.isNotEmpty()) {
            // 取出来
            val node = stack.pop()
            result.add(node.`val`)
            // 先放右
            node.right?.let {
                stack.push(it)
            }
            node.left?.let {
                stack.push(it)
            }
        }

        return result
    }

    /**
     * 前序深度遍历 根 左 右
     * 第二种算法，比较通用
     * 非递归
     */
    fun preorderTraversal2(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack  = LinkedList<TreeNode>()
        if (root == null) return emptyList()
        var head = root
        while (head != null || stack.isNotEmpty()) {
            // 先不断的遍左子树
           while (head != null) {
               result.add(head.`val`) // 保存根节点
               stack.push(head)
               head = head.left
           }
            // 转向右子树
            head = stack.pop().right
        }

        return result
    }


    /**
     * 二叉树的中序遍历
     * 左 中 右
     * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
     */
    fun inorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack  = LinkedList<TreeNode>()
        if (root == null) return emptyList()
        var head = root
        // head 为 root的右节点是 stack 为空
        while (head != null || stack.isNotEmpty()) {
            // 先不停的加进去左子树
            while (head != null) {
                stack.push(head)
                head = head.left
            }
            // 等到左子树没了
            val node = stack.pop()
            result.add(node.`val`)
            // 再加进去右子树
            head = node.right
        }

        return result
    }

    /**
     * 后续遍历
     * 左右 根
     * 前序为 根 左 右，将前序翻转就是 右 左 根，然后将遍历改为先遍历右边 就变成 左 右 根了
     * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/submissions/
     */
    fun backorderTraversal(root: TreeNode?): List<Int> {
        val result = LinkedList<Int>()
        val stack  = LinkedList<TreeNode>()
        if (root == null) return emptyList()
        var head = root
        // head 为 root的右节点是 stack 为空
        while (head != null || stack.isNotEmpty()) {
            // 先不停的加进去左子树 改为先加右子树
            while (head != null) {
                result.addFirst(head.`val`) // 添加到头部，将顺序 改为右 左 根
                stack.addFirst(head)
                head = head.right
            }
            val node = stack.pollLast()
            head = node.left
        }

        return result
    }


    /**
     * N叉树 前序遍历 根 左 右
     * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
     */

    fun preorderN(root: Node?): List<Int> {
        val list = mutableListOf<Int>()
        val stack  = LinkedList<Node>()
        if (root == null) return emptyList()
        stack.add(root)
        while (stack.isNotEmpty()) {
            val current = stack.pop() // 取出当前遍历的节点
            list.add(current.`val`)
            // 将子树 从右往左添加
            for (i in current.children.size - 1 downTo 0) {
                if (current.children[i] != null) {
                    stack.push(current.children[i]!!)
                }
            }
        }
        return list
    }

    /**
     * N叉树后序遍历
     * 左 右 根 先 转化为根 右 左 （前序变种）
     */
    fun postorderN(root: Node?): List<Int> {
        val list = LinkedList<Int>()
        val stack  = LinkedList<Node>()
        if (root == null) return emptyList()
        stack.add(root)
        while (stack.isNotEmpty()) {
            val current = stack.pop()
            list.addFirst(current.`val`)
            current.children.filterNotNull().forEach {
                stack.push(it)
            }
        }
        return list
    }


    /**
     * 二叉树最大深度
     * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
     */

    // 解法 1 递归，非递归也好做，遍历节点就行，叶子结点就保存最大深度

    fun maxDepth(root: TreeNode?): Int {
        tranversal(root, 1)
        return maxDepth
    }

    var maxDepth = 0
    fun tranversal(root: TreeNode?, depth : Int) {
        if (root == null) { // 说明上个节点是叶子结点
            if (maxDepth < depth - 1) {
                maxDepth = depth - 1
            }
            return
        }
        tranversal(root.left, depth + 1)
        tranversal(root.right, depth + 1)
    }

    /**
     * N叉树最大深度
     */
    fun tranversalN(root: Node?, depth : Int) {
        if (root == null) { // 说明上个节点是叶子结点
            if (maxDepth < depth - 1) {
                maxDepth = depth - 1
            }
            return
        }
        if (root.children.isEmpty()) {
            tranversalN(null, depth + 1)
        }
        root.children.forEach {
            tranversalN(it, depth + 1)
        }
    }


    /**
     * 打印二叉树右视图
     * https://leetcode-cn.com/problems/binary-tree-right-side-view/
     */
    fun rightSideView(root: TreeNode?): List<Int> {
        val queue = LinkedList<TreeNode>()
        val result = mutableListOf<Int>()
        if (root == null) return emptyList()
        queue.offer(root)
        while (queue.isNotEmpty()) {
           // 遍历
            for (i in 0 until queue.size) {
                val node = queue.pop()
                if (i == 0) {
                    result.add(node.`val`)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
                if (node.left != null) {
                    queue.offer(node.left)
                }
            }

        }

        return result

    }

    /**
     * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/solution/er-cha-sou-suo-shu-de-zui-jin-gong-gong-zu-xian-26/
     * 二叉搜索树最近公共祖先
     * 可以很容易的找到路径，将两个路径保存下来，然后遍历 最后一个相同的节点就是公共祖先
     * [2,1] 2/1
     */

    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        var head = root
        val pList = mutableListOf<TreeNode>()
        val qList = mutableListOf<TreeNode>()
        if (p == null || q == null) return null
        while (head != null && head != p) {
            pList.add(head)
            if (head.`val` < p.`val`) {
                //在右侧
                head = head.right
            } else {
                head = head.left
            }
        }
        if (head != null) {
            pList.add(head)
        }
        head = root
        while (head != null && head != q) {
            qList.add(head)
            if (head.`val` < q.`val`) {
                //在右侧
                head = head.right
            } else {
                head = head.left
            }
        }
        if (head != null) {
            qList.add(head)
        }

        var i = 0
        while (i < qList.size && i < pList.size) {
            if (qList[i] != pList[i]) {
                break
            }
            i ++
        }
        return qList[i - 1]
    }

    /**
     * 变种，非搜索二叉树二叉树最近公共祖先，先找第一个节点，再找第二个节点
     * 保存两次遍历的路径
     */

    fun lowestCommonAncestor2(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        val pList = LinkedList<TreeNode>()
        val qList = LinkedList<TreeNode>()
        if (p == null || q == null) return null
        tranversal(pList, root, p) // 保存p的路径
        tranversal(qList, root, q) // 保存q的路径
        var i = 0
        while (i < qList.size && i < pList.size) {
            if (qList[i] != pList[i]) {
                break
            }
            i ++
        }
        return qList[i - 1]
    }
    fun tranversal(list : LinkedList<TreeNode>, node : TreeNode?, source : TreeNode) : Boolean {
        if (node == source) {
            list.push(node)
            return true
        }
        if (node == null) return false
        val left = tranversal(list, node.left, source)
        if (left) {
            list.push(node)
            return true
        }
        val right = tranversal(list, node.right, source)
        if (right) {
            list.push(node)
            return true
        }
        return false
    }


    /**
     * 更高级写法
     *
     */

    fun lowestCommonAncestor3(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || root == p || root == q) return root // 找到一个相等的就返回root，找不到返回null

        val left = lowestCommonAncestor3(root.left, p, q) // 在左子树找到一个节点
        val right = lowestCommonAncestor3(root.right, p, q) // 在右子树找到一个节点

        if (left != null && right != null) {
            // 如果在左边右边都找到的话，这个节点就是最终的节点
            return root
        }

        if (left != null){
            return left
        }

        if (right != null) {
            return right
        }

        return null
    }


    /**
     * 两个链表第一个重合的节点
     * 重合指的是对象相等
     * https://leetcode-cn.com/problems/3u1WK4/
     * 解法一 暴力两层循环
     * 解法二 哈希表 第一次循环存下来，第二次循环在哈希表里寻找，找到就是
     * 下面是解法三 双指针
     * A + B 的长度 = B + A
     * 找到相等的就返回
     */

    fun getIntersectionNode(headA:ListNode?, headB:ListNode?):ListNode? {
        var a = headA
        var b = headB
        while (a != b) {
            a = a?.next ?: headB
            b = b?.next ?: headA
        }
        return a
    }


    /**
     * https://leetcode-cn.com/problems/remove-duplicate-node-lcci/solution/
     * 移除链表重复节点
     * 双层循环
     */


    fun removeDuplicateNodes(head: ListNode?): ListNode? {
        var root = head
        while (root != null) {
            var cur = root
            while (cur?.next != null) {

                if (cur.next!!.`val` == root.`val`){
                    // 删除掉 cur.next
                    cur.next = cur.next!!.next
                } else {
                    cur = cur.next
                }
            }
            root = root.next
        }

        return head
    }

    /**
     * 二叉树节点最大距离
     * 可以转化为子问题 左子树的最大深度，右子树的最大深度
     * https://leetcode-cn.com/problems/diameter-of-binary-tree/solution/er-cha-shu-de-zhi-jing-by-leetcode-solution/
     */


    var max = 0
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        depth(root)
        return max - 1
    }

    fun depth(root: TreeNode?) : Int{
        if (root == null) {
            return 0
        }
        val left = depth(root.left) // 左子树的深度
        val right = depth(root.right) // 右子树的深度
        max = Math.max(max, left + right + 1)

        // 当前结点的深度
        return Math.max(left, right) + 1
    }


    /**
     * 冒泡排序
     * 将排好序的放后面
     */

    fun maopao(array : IntArray) {
        for (i in 0 until array.size - 1) {
            // 每趟比较会将倒数第i个排好
            for (j in 0 until array.size - i - 1) {
                if (array[j] > array[j+1]) {
                    val temp = array[j+1]
                    array[j + 1] = array[j]
                    array[j] = temp
                }
            }
        }
    }

    /**
     * 是否包含子串
     */

    fun main(args: Array<String>) {
        val read = Scanner(System.`in`)

        val sub = read.next()
        val str = read.next()
        var i = 0
        var result = 0
        // 外层循环
        sub.forEach {
            for (j in i until str.length) {
                if (it == str[j]) {
                    i = j + 1
                    result ++
                    break
                }
            }
        }

        if (result == sub.length) {
            print(1)
        } else {
            print(0)
        }


    }

    fun shuiguo() {
        val read = Scanner(System.`in`)
        var result = 0

        val aFruit = read.next().split(" ")
        val bFruit = read.next().split(" ")
        var aSuit = read.next().split(" ")
        val bSuit = read.next().split(" ")

        val aWeight = aFruit[0].toInt()
        val aPrice = aFruit[1].toInt()

        val bWeight = bFruit[0].toInt()
        val bPrice = bFruit[1].toInt()
        if (aWeight < aSuit[0].toInt() && aWeight < bSuit[0].toInt() &&
        bWeight < bSuit[0].toInt() && bWeight < aSuit[0].toInt()) {
            print(aWeight * aPrice + bWeight * bPrice)
        } else {

        }

    }


    /**
     * 爬楼梯 最简单的动态规划
     * https://leetcode-cn.com/problems/climbing-stairs/
     * f(x) = f(x - 1) + f(x - 2)
     */

    fun climbStairs(n: Int): Int {
        if (n < 2) {
            return n
        }
        val dp = IntArray(n + 1)

        dp[1] = 1
        dp[2] = 2

        for (i in 3 until n + 1) {
            dp[i] = dp[i-1] + dp[i-2]
        }
        return dp[n]
    }

    /**
     * https://leetcode-cn.com/problems/add-two-numbers/
     * 链表 两数相加
     */

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var root : ListNode? = null
        var l1Head = l1
        var l2Head = l2
        var current : ListNode? = null
        var carrier = 0
        while (l1Head != null || l2Head != null) {

            // 空也没事，就当做是0
            val num1 = l1Head?.`val` ?: 0
            val num2 = l2Head?.`val` ?: 0
            val sum = num1 + num2 + carrier
            if (root == null) {
                root = ListNode(sum % 10)
                current = root
            } else {
                current!!.next = ListNode(sum % 10)
                current = current.next
            }

            carrier = sum / 10


            l1Head = l1Head?.next
            l2Head = l2Head?.next
        }

        // 如果遍历完了还有进位
        if (carrier == 1) {
            current!!.next = ListNode(1)
        }

        return root

    }


    /**
     * 回文链表
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnv1oc/
     * 快慢指针，后半部分链表翻转
     * 快指针每次移动两次，慢指针每次移动一次
     */

    fun isPalindrome(head: ListNode?): Boolean {
        fun reverse(root : ListNode?) : ListNode?{
            var prev : ListNode? = null
            var current = root
            while (current != null) {
                val temp = current.next
                current.next = prev
                prev = current
                current = temp
            }
            return prev
        }
        var slow = head
        var fast = head
        if (fast?.next != null) {
            fast = fast.next!!.next
            slow = slow!!.next
        }

        // 奇数个结点,slow向后移一位
        if (fast != null) {
            slow = slow?.next
        }

        // 翻转后半部分节点
        slow = reverse(slow)

        if (head == null) return false
        fast = head

        // 如果为奇数最后一个不用比较，如果为偶数，两个指针同时到达
        while (slow != null && fast != null) {
            if (slow.`val` != fast!!.`val`) {
                return false
            }
            slow = slow.next
            fast = fast.next
        }


        return true

    }


    /**
     * 判断是否为环形链表
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnwzei/
     * 快慢指针
     */

    fun hasCycle(head: ListNode?): Boolean {
        var slow = head //慢指针
        var fast = head // 快指针
        while (slow != null && fast?.next != null) {
            fast = fast.next!!.next // 快指针走两步。慢指针走一步，如果能遇到就有环
            slow = slow.next
            if (slow == fast) {
                return true
            }
        }
        return false
    }

    /**
     * 环形节点的开始
     */

    fun hasCycle2(head: ListNode?): Boolean {
        var slow = head //慢指针
        var fast = head // 快指针
        while (slow != null && fast?.next != null) {
            fast = fast.next!!.next // 快指针走两步。慢指针走一步，如果能遇到就有环
            slow = slow.next
            if (slow == fast) {
                return true
            }
        }
        return false
    }

    /**
     * 验证二叉搜索树
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn08xg/
     */

    fun isValidBST(root: TreeNode?): Boolean {
        /**
         * 向左遍历的节点，最大值要小于 max
         * 向右遍历的节点，最小值要大于最大值
         */
        fun isValidBST(root: TreeNode?, min: Int, max : Int): Boolean {
            if (root == null) return true
            if (root.`val` <= min || root.`val` >= max) {
                return false
            }
            val left = isValidBST(root.left, min, root.`val`)
            val right = isValidBST(root.right, root.`val`, max)


            return left && right
        }
        return isValidBST(root, Int.MIN_VALUE, Int.MAX_VALUE)
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn7ihv/
     * 判断是否为镜像二叉树
     * 核心： 左子树的左节点 等于右子树的右节点，左子树的右节点等于 右子树的左节点
     *
     */

    fun isSymmetric(root: TreeNode?): Boolean {

        /**
         * @param
         */
        fun isSymmetric(left : TreeNode?, right : TreeNode?) : Boolean{

            // 所有叶子结点返回的都是true
            if (left == null && right == null) {
                return true
            }
            // 当遍历到非叶子结点时，如果有一个为null，或者两个值不相等，那就不是对称的
            if (left == null || right == null || left.`val` != right.`val`) {
                return false
            }

            return isSymmetric(left.left, right.right) && isSymmetric(left.right , right.left)
        }

        if (root == null) return true
        return isSymmetric(root.left, root.right)
    }


    /**
     * 方法二 使用队列来做，利用按层遍历的性质
     */

    fun isSymmetric2(root: TreeNode?): Boolean {
        if(root == null) return true
        val queue = LinkedList<TreeNode?>()
        queue.offer(root.left)
        queue.offer(root.right)
        while (queue.isNotEmpty()) {
            val left = queue.pop()
            val right = queue.pop()
            if (left == null && right == null) {
                continue
            }
            if (left == null || right == null) {
                return false
            }
            if (left.`val` != right.`val`) {
                return false
            }
            queue.offer(left.left)
            queue.offer(right.right)
            queue.offer(left.right)
            queue.offer(right.left)
        }

        return true
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnldjj/
     * 返回层序遍历结果的值
     */

    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val queue = LinkedList<TreeNode>()
        if (root == null) return emptyList()
        queue.offer(root)
        while (queue.isNotEmpty()) {
            val list = mutableListOf<Int>()
            val size = queue.size
            for (i in 0 until size) {
                val node = queue.pop()
                list.add(node.`val`)
                if (node.left != null) {
                    queue.offer(node.left)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
            }
            result.add(list)
        }
        return result
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xninbt/
     * 将有序数组转为二叉搜索树
     * 递归，每次取数组的中间值，然后创建节点
     */
    fun sortedArrayToBST(nums: IntArray): TreeNode? {
        fun sortedArrayToBST(nums: IntArray, start : Int, end : Int): TreeNode? {
            if (start > end) return null
            val mid = (start + end) / 2
            val root = TreeNode(nums[mid])
            root.left = sortedArrayToBST(nums, start, mid - 1)
            root.right = sortedArrayToBST(nums, mid + 1, end)
            return root
        }

        return sortedArrayToBST(nums, 0, nums.size - 1)
    }


    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnumcr/
     * 合并两个有序数组，数组1后面全是0
     * 从后向前比，把大的值放到后面
     */

    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        var end = m + n - 1
        var i = m - 1
        var j = n - 1

        while (j > 0) {
            if (nums1[i] > nums2[j] && i > 0) {
                nums1[end--] = nums1[i--]
            } else {
                nums1[end--] = nums2[j--]
            }
        }
    }

    /**
     * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnto1s/
     * 第一个出错的版本
     */

    fun firstBadVersion(n: Int) : Int {
        fun isBadVersion(int: Int) : Boolean {
            return true
        }
        var start = 0
        var end = n

        while (start < end) {
            val mid = (end + start) / 2
            // mid版本没问题，那有问题的还在后面
            if (!isBadVersion(mid)) {
                start = mid + 1
            } else {
                // 有问题的在前面
                end = mid
            }
        }
        return start //找到最后，start就是第一个
    }


    /**
     * 判断ipv4地址是否合法
     */
    fun ip(str : String) : Boolean {
        if (str.isEmpty()) return false
        val array = str.split(".")
        if (array.size != 4) return false
        var j = 0
        array.forEach {
            // it进行遍历
            if (it.length > 3) return false
            var num = 0
            for (i in it.indices) {
                if (it[i] - '0' > 9 || it[i] - '0' < 0) return false
                if (num == 0 && it[i] - '0' == 0) return false
                num = num * 10 + (it[i] - '0')
            }
            if (num > 255) return false
            if (j == 0 && num == 0) return false
            j++
        }

        return true
    }


    /**
     * 移动元素
     * 移除数组中所有与给定值相同的元素
     * https://leetcode-cn.com/leetbook/read/all-about-array/x9p1iv/
     * 双指针，left指针表示不等元素的末尾
     */

    fun removeElement(nums: IntArray, `val`: Int): Int {
        var left = 0 // 只有不等val的时候才++
        nums.forEach {
            if (it != `val`) {
                nums[left++] = it
            }
        }
        return left
    }


    /**
     * 连续重复次数最多的字符
     */
    fun maxLengthChar(str: String) : Char? {
        if (str.isEmpty()) return null
        var left = 0
        var right = 0
        var max = 0
        var maxLengthChar = str[0]
        while (right < str.length) {
            if (right + 1 >= str.length || str[right] != str[right + 1]) {
                if (max < right - left + 1) {
                    max = right - left + 1
                    maxLengthChar = str[right]
                }
                left = right + 1
            }
            right ++
        }
        return maxLengthChar
    }

    /**
     * https://leetcode-cn.com/leetbook/read/all-about-array/x9nivs/
     * 原地删除数字，最多出现两次
     */
    fun removeDuplicatesMax2(nums: IntArray): Int {
        var left = 0
        var right = 0
        val map = HashMap<Int, Int>()
        while (right < nums.size) {
            //当个数小于2的时候直接赋值，大于2的时候略过，寻找下一个不重复的字符
            if (map[nums[right]] == null || map[nums[right]]!! < 2) {
                nums[left] = nums[right]
                left ++
            }
            if (map[nums[right]] == null) {
                map[nums[right]] = 1
            } else {
                map[nums[right]] = map[nums[right]]!! + 1
            }
            right++
        }
        return left
    }


    /**
     * 数组的最大子序和
     * 动态规划
     * 当前元素的最大值 是 f(i) = max(f(i - 1) + num[i], num[i])
     * https://leetcode-cn.com/problems/maximum-subarray/
     */

    fun maxSubArray(nums: IntArray): Int {
        var pre = nums[0] // 这个是f(i - 1)
        var max = nums[0] //这个代表最大值
        for (i in 1 until nums.size) {
            pre = Math.max(pre + nums[i], nums[i]) // 求出每次的f(i)
            max = Math.max(max, pre) // 记录最终的最大值
        }

        return max
    }

    /**
     * 罗马数组转整数
     * https://leetcode-cn.com/problems/roman-to-integer/
     * 如果右边的大于左边的，则需要减去左边，否则就加上左边
     */

    fun romanToInt(s: String): Int {
        val map = HashMap<Char, Int>().apply {
            put('I', 1)
            put('V', 5)
            put('X', 10)
            put('L', 50)
            put('C', 100)
            put('D', 500)
            put('M', 1000)
        }
        if (s.length == 1) {
            return map[s[0]]!!
        }
        var max = 0
        for (i in s.indices) {
            val current = map[s[i]]!!
            if (i < s.length - 1) {
                val next = map[s[i + 1]]!!
                if (next > current) {
                    max -= current
                } else {
                    max +=current
                }
            }  else {
                max += current
            }
        }
        return max
    }


    /**
     * https://leetcode-cn.com/problems/implement-strstr/
     * 字符串匹配
     */

    fun strStr1(haystack: String, needle: String): Int {
        val n = haystack.length
        val m = needle.length
        if (m == 0) return 0
        if (haystack == needle) return 0
        for (i in 0 until n) {
            if (i > n - m) return -1
            var flag = true
            for (j in 0 until m) {
                if (haystack[i + j] != needle[j]) {
                    flag = false
                    break
                }
            }
            if (flag) {
                return i
            }
        }
        return -1
    }

    /**
     * https://leetcode-cn.com/problems/search-insert-position/
     * 二分查找
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        var start = 0
        var end = nums.size - 1
        var result = nums.size
        while (start <= end) {
            val mid = (start + end) / 2
            if (nums[mid] == target) {
                return mid
            } else if (nums[mid] < target) {
                start = mid + 1
            } else {
                result = mid
                end = mid - 1
            }
        }
        return result
    }


    /**
     * 寻找第一个不重复的字符
     */
    fun firstChar(str : String) : Char? {
        if (str.isEmpty()) return null
        if (str.length == 1) return str[0]
        val mutable = mutableMapOf<Char, Int>()
        val map = HashMap<Char, Int>()
        // 先遍历一遍统计每个字符出现的次数
        str.forEach {
            if (map.containsKey(it)) {
                map[it] = map[it]!! + 1
            } else {
                map[it] = 1
            }
        }
        str.forEach {
            if (map.containsKey(it) && map[it] == 1) {
                return it
            }
        }
        return null
    }


    /**
     * 二分查找
     */
    fun erfen(nums : IntArray, target : Int) : Boolean{
        var start = 0
        var end = nums.size - 1
        while (start <= end) {
            val mid = (end - start) / 2 + start
            println(mid)
            if (nums[mid] == target) {
                return true
            } else if (nums[mid] < target) {
                start = mid + 1
            } else {
                end = mid - 1
            }
        }
        return false
    }


    /**
     * https://leetcode-cn.com/problems/permutations/
     * 一维数组全排列
     * 回溯法，每次递归完成的时候就回溯，因为把自己添加进去了，
     */

    fun permute(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        fun backup(mutableList: MutableList<Int>) {
            if (mutableList.size == nums.size) {
                result.add(ArrayList<Int>(mutableList))
                return
            }
            nums.forEach {
                if (!mutableList.contains(it)) {
                    mutableList.add(it)
                    backup(mutableList) // 当backup返回的时候，将添加的数据移除掉，就可以添加其他数据了
                    mutableList.remove(it)
                }
            }
        }
        backup(mutableListOf())
        return result
    }


    /**
     * https://leetcode-cn.com/problems/coin-change/
     * 零钱兑换
     * 动态规划
     * 假设 组成i 的最小个数是 f(i) 那 fi(i) = min(f(i - c1) ,f(i - c2),f(i - c3)) + 1
     */

    fun coinChange(coins: IntArray, amount: Int): Int {
        val dp = IntArray(amount + 1)
        dp[0] = 0
        for (i in 1 until amount + 1) {
            dp[i] = Int.MAX_VALUE - 1 // 减一 防止下面+1的时候溢出
            coins.forEach {
                if (i >= it) {
                    dp[i] = Math.min(dp[i], dp[i - it] + 1)
                }
            }
        }
        return if (dp[amount] == Int.MAX_VALUE - 1) -1 else dp[amount]
    }








}