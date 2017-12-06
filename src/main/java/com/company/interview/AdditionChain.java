package com.company.interview;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AdditionChain {

    private static final Map<Integer, Integer> lookaheadMap = new HashMap<>();

    public static boolean search(Stack<Integer> chain,
                                 int target,
                                 int depth) {
        Set<Integer> candidates = new TreeSet<>(Collections.reverseOrder());

        for (Integer i1 : chain) {
            for (Integer i2 : chain) {
                candidates.add(i1 + i2);
            }
        }

        if (depth == 1) {
            int chain_len = chain.size();
            for (int cand : candidates) {
                lookaheadMap.putIfAbsent(cand, chain_len);
            }
            return candidates.contains(target);
        }

        int max_scale = 1 << (depth - 1);
        for (Integer cand : candidates) {
            if (cand * max_scale < target) {
                // System.out.println("Rejected chain prefix: " + chain + " + " + cand + " < " + target + " @ depth: " + depth);
                break;
            }
            chain.push(cand);
            boolean found = search(chain, target, depth - 1);
            chain.pop();
            if (found) {
                return true;
            }
        }

        return false;
    }

    private static int solve(int n) {
        if (n == 1) {
            return 0;
        }
        Stack<Integer> chain = new Stack<>();
        chain.add(1);

        if (lookaheadMap.containsKey(n)) {
            return lookaheadMap.get(n);
        } else {
            for (int d = 1; ; d++) {
                if (search(chain, n, d)) {
                    return d;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fis = new FileReader("c:\\Users\\anita\\Desktop\\addition_verify.txt");
        StringBuilder contents = new StringBuilder();
        char[] buf = new char[4096];
        while (true) {
            int size = fis.read(buf);
            if (size == -1) {
                break;
            }
            contents.append(buf, 0, size);
        }
        List<Integer> verify = new ArrayList<>();
        for (String line : contents.toString().split("\n")) {
            String[] vals = line.split(" ");
            if (vals.length != 2) {
                System.out.println("Debug: \"" + line + "\"");
            } else {
                verify.add(Integer.parseInt(vals[1]));
            }
        }
        System.out.println("Has: " + verify.size() + " entries");

        long startNanos = System.nanoTime();
        for (int n = 2; n <= 3000; n++) {
            int actual = solve(n);
            if (n - 1 < verify.size()) {
                int expected = verify.get(n - 1);
                if (expected != actual) {
                    System.out.println("Debug " + n + "\tactual:" + actual + "\texpected:" + expected);
                }
            }
            if (n % 100 == 0) {
                long endNanos = System.nanoTime();
                long seconds = TimeUnit.NANOSECONDS.toSeconds(endNanos - startNanos);
                System.out.println("Solved up to " + n + " in " + seconds + " seconds");
            }
        }

    }
}
