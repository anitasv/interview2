package com.company.interview.statistics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CasStatistic implements Statistic {

    private AtomicInteger minSoFar = new AtomicInteger(Integer.MAX_VALUE);
    private AtomicInteger maxSoFar = new AtomicInteger(Integer.MIN_VALUE);

    private AtomicLong sum = new AtomicLong(0);

    private AtomicLong sum_sq = new AtomicLong(0);

    private AtomicInteger count = new AtomicInteger();

    public void event(int value) {
        sum.addAndGet(value);
        sum_sq.addAndGet(((long)value) * value);
        count.incrementAndGet();

        while (true) {
            int currentMin = minSoFar.get();
            if (value < currentMin) {
                if (minSoFar.compareAndSet(currentMin, value)) {
                    break;
                }
            } else {
                break;
            }
        }
        while (true) {
            int currentMax = maxSoFar.get();
            if (value > currentMax) {
                if (maxSoFar.compareAndSet(currentMax, value)) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    public float mean() {
        return ((float) sum.get()) / count.get();
    }

    public int minimum() {
        return minSoFar.get();
    }

    public int maximum() {
        return maxSoFar.get();
    }

    public float variance() {
        return (((float) (sum_sq.get()) / count.get())) - sq(mean());
    }

    private static float sq(float mean) {
        return mean * mean;
    }
}
