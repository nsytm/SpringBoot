package com.example.marscode;

/**
 * @author 天纵神威
 * @date 2024/11/19
 * @description 数字插入问题
 */
public class MaxInsertNumber {

    public static int solution(int a, int b) {
        String strA = String.valueOf(a);
        String strB = String.valueOf(b);
        String maxNumber = "";

        // 遍历所有插入点
        for (int i = 0; i <= strA.length(); i++) {
            // 将 b 插入到 a 的第 i 个位置
            String newNumber = strA.substring(0, i) + strB + strA.substring(i);
            // 比较当前结果是否为最大值
            if (newNumber.compareTo(maxNumber) > 0) {
                maxNumber = newNumber;
            }
        }

        return Integer.parseInt(maxNumber);
    }

    /**
     * 输入：a = 76543, b = 4
     * 输出：765443
     * 解释：第一个数字是一个任意的正整数，而第二个数字是一个非负整数。
     * 将第二个数字 b 插入到第一个数字 a 的某个位置，以形成一个最大的数字。
     */
    public static void main(String[] args) {
        System.out.println(solution(76543, 4) == 765443);
        System.out.println(solution(1, 0) == 10);
        System.out.println(solution(44, 5) == 544);
        System.out.println(solution(6667, 6) == 66676);
        System.out.println(solution(12, 15) == 1512);
    }

}