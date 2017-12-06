package com.company.interview.expr;

import java.util.ArrayList;
import java.util.List;

public class IterablesTest {

    public static void main(String[] args) {

        List<Integer> numbers1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers1.add(i);
        }

        List<Integer> numbers2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers2.add(i * 30);
        }

        Iterable<Integer> allNumbers = Iterables.concat(numbers1, numbers2);

        for (int k : allNumbers) {
            System.out.println(k);
        }


        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(String.valueOf(i));
        }

        Iterable<String> cross = Iterables.cross(numbers1, strings, (n, s) -> (n + s));

        for (String k : cross) {
            System.out.println(k);
        }
    }
}
