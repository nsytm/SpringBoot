package com.example.leetcode;

/**
 * @author 天纵神威
 * @date 2024/12/17
 * @description 只出现一次的数字
 */
public class SingleNumber {

    public static int solution(int[] nums) {

        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * <p>
     * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
     * <p>
     * 示例 1 ：
     * 输入：nums = [2,2,1]
     * 输出：1
     * <p>
     * 示例 2 ：
     * 输入：nums = [4,1,2,1,2]
     * 输出：4
     * <p>
     * 示例 3 ：
     * 输入：nums = [1]
     * 输出：1
     * <p>
     * 提示：
     * 1 <= nums.length <= 3 * 104
     * -3 * 104 <= nums[i] <= 3 * 104
     * 除了某个元素只出现一次以外，其余每个元素均出现两次。
     */
    public static void main(String[] args) {

        // 了解异或操作符 ^
        System.out.println(solution(new int[]{2, 2, 1}));
        System.out.println(solution(new int[]{4, 1, 2, 1, 2}));
        System.out.println(solution(new int[]{1}));

    }

}