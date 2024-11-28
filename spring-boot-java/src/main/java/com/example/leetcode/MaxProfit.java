package com.example.leetcode;

/**
 * @author 天纵神威
 * @date 2024/11/27
 * @description 最大利润问题
 */
public class MaxProfit {

    public static int solution(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;

        for (int price : prices) {
            // 如果当前价格 price 小于 minPrice，更新 minPrice。
            // 如果当前价格减去 minPrice（即卖出的潜在利润）大于 maxProfit，更新 maxProfit。
            if (price < minPrice) {
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }

        return maxProfit;
    }

    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * <p>
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * <p>
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * <p>
     * 示例 1：
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * <p>
     * 示例 2：
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public static void main(String[] args) {
        // 输出 5
        System.out.println(solution(new int[]{7, 1, 5, 3, 6, 4}));
        // 输出 0
        System.out.println(solution(new int[]{7, 6, 4, 3, 1}));
    }

}