package com.example.marscode;

import java.util.TreeSet;

/**
 * @author 天纵神威
 * @date 2024/11/25
 * @description 获取目标分数
 */
public class FindTargetScore {

    public static int solution(int n, int[] nums) {
        int[] a = new int[0];
        // TreeSet 默认升序
        TreeSet<Integer> numTreeSet = new TreeSet<>();
        for (int num : nums) {
            numTreeSet.add(num);
        }
        if (numTreeSet.size() >= 3) {
            // 第三大分数的索引
            int i = numTreeSet.size() - 3;
            return numTreeSet.toArray(new Integer[0])[i];
        } else {
            return numTreeSet.getLast();
        }
    }

    /**
     * 如果分数中有三个或以上不同的分数，返回其中第三大的分数；
     * 如果不同的分数只有两个或更少，那么将返回最大的分数作。
     */
    public static void main(String[] args) {
        System.out.println(solution(3, new int[]{3, 2, 1}) == 1);
        System.out.println(solution(2, new int[]{1, 2}) == 2);
        System.out.println(solution(4, new int[]{2, 2, 3, 1, 4}) == 2);
    }

}