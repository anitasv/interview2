package com.company.interview;

import java.util.ArrayList;
import java.util.List;

public class ComputeExp {

    public static void main(String[] args) {

        double e1 = standardWay();
        double e2 = newWay();

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(Double.doubleToRawLongBits(e1));
        System.out.println(Double.doubleToRawLongBits(e2));
    }

    private static double newWay() {
        double sum = 0;
        List<Double> toAdd = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {
            toAdd.add(1.0 / i / i);
        }
        for (int i = 0; i < toAdd.size(); i++) {
            sum += toAdd.get(toAdd.size() - i - 1);
        }
        return sum;
    }

    private static double standardWay() {

        double sum = 0;
        for (int i = 1; i <= 100000; i++) {
            sum += 1.0 / i / i;
        }
        return sum;
    }

}
