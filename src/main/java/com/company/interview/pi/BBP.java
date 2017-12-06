package com.company.interview.pi;

import java.math.BigDecimal;

/**
 * Created by anita on 15-Mar-17.
 */
public class BBP {

    public static void main(String[] args) {

        double pi = 0;

        double den = 1;
        for (int k = 0; k < 11; k++) {
            double ek = 8 * k;
            double frac = 4 / ( ek + 1) - 2 / (ek + 4) - 1 / (ek + 5) - 1 / (ek +6);
            pi += (frac / den);
            den *= 16;
            System.out.println(pi);
        }
    }
}
