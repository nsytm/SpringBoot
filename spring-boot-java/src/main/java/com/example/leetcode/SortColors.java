package com.example.leetcode;

import java.util.Arrays;

/**
 * @author 天纵神威
 * @date 2024/12/20
 * @description 颜色分类
 */
public class SortColors {

    public static void solution(int[] nums) {
        int current = 0;
        int left = 0, right = nums.length - 1;

        while (current <= right) {
            if (nums[current] == 0) {
                // 当前值是红色(0)，与左指针交换，左指针和当前指针右移
                swap(nums, current, left);
                current++;
                left++;
            } else if (nums[current] == 1) {
                // 当前值是白色(1)，只需移动当前指针
                current++;
            } else if (nums[current] == 2) {
                // 当前值是蓝色(2)，与右指针交换，右指针左移
                swap(nums, current, right);
                right--;
            }
        }
    }

    /**
     * 交换数组中两个元素的位置
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地 对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * <p>
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * <p>
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
     * <p>
     * 示例 1：
     * 输入：nums = [2,0,2,1,1,0]
     * 输出：[0,0,1,1,2,2]
     * <p>
     * 示例 2：
     * 输入：nums = [2,0,1]
     * 输出：[0,1,2]
     * <p>
     * 提示：
     * n == nums.length
     * 1 <= n <= 300
     * nums[i] 为 0、1 或 2
     * <p>
     * 进阶：
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     */
    public static void main(String[] args) {

        int[] nums = {2, 0, 2, 1, 1, 0};
        solution(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 2, 0};
        solution(nums);
        System.out.println(Arrays.toString(nums));

    }

}