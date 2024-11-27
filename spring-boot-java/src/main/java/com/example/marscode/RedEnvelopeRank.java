package com.example.marscode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author 天纵神威
 * @date 2024/11/27
 * @description 红包排名
 */
public class RedEnvelopeRank {

    public static List<String> solution(int n, List<String> s, List<Integer> x) {

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class Person {

            /**
             * 姓名
             */
            String name;

            /**
             * 金额
             */
            int amount;

        }

        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            personList.add(new Person(s.get(i), x.get(i)));
        }

        // 按金额降序排序，金额相同时保持原顺序
        // indexOf 返回对象在列表中的首次出现位置
        personList.sort(Comparator.comparing(Person::getAmount)
                .reversed()
                .thenComparingInt(personList::indexOf));

        return personList.stream().map(Person::getName).toList();
    }

    /**
     * n 表示用户数量
     * s 表示用户列表，顺序是抢红包的顺序
     * x 表示用户抢的红包金额
     */
    public static void main(String[] args) {
        System.out.println(solution(4, Arrays.asList("a", "b", "c", "d"), Arrays.asList(1, 2, 2, 1)).equals(Arrays.asList("b", "c", "a", "d")));
        System.out.println(solution(3, Arrays.asList("x", "y", "z"), Arrays.asList(100, 200, 200)).equals(Arrays.asList("y", "z", "x")));
        System.out.println(solution(5, Arrays.asList("n", "m", "o", "p", "q"), Arrays.asList(50, 50, 30, 30, 20)).equals(Arrays.asList("n", "m", "o", "p", "q")));
    }

}