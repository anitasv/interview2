package com.company.interview.statistics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;

public class AccStatistic implements Statistic {

    private LongAccumulator minSoFar = new LongAccumulator(Math::min, Integer.MAX_VALUE);
    private LongAccumulator maxSoFar = new LongAccumulator(Math::max, Integer.MIN_VALUE);

    private LongAccumulator sum = new LongAccumulator((x, y) -> x + y, 0);
    private LongAccumulator sum_sq = new LongAccumulator((x, y) -> x + y, 0);

    private AtomicInteger count = new AtomicInteger();

    public void event(int value) {
        sum.accumulate(value);
        sum_sq.accumulate(((long)value) * value);
        count.incrementAndGet();
        minSoFar.accumulate(value);
        maxSoFar.accumulate(value);
    }

    public float mean() {
        return ((float) sum.get()) / count.get();
    }

    public int minimum() {
        return (int) minSoFar.get();
    }

    public int maximum() {
        return (int) maxSoFar.get();
    }

    public float variance() {
        return (((float) (sum_sq.get()) / count.get())) - sq(mean());
    }

    private static float sq(float mean) {
        return mean * mean;
    }

}
