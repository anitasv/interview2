package com.company.interview.statistics;

public class FloatStatistic implements Statistic {

    private volatile int minSoFar = Integer.MAX_VALUE;

    private volatile int maxSoFar = Integer.MIN_VALUE;

    private volatile long sum = 0;

    private volatile float sum_sq = 0;

    private volatile int count = 0;

    public void event(int value) {
        sum = Math.addExact(sum, value);
        sum_sq = sum_sq + ((float)value) * value;
        count++;
        if (value < minSoFar) {
            minSoFar = value;
        }
        if (value > maxSoFar) {
            maxSoFar = value;
        }
    }

    public float mean() {
        return (float) sum / count;
    }

    public int minimum() {
        return minSoFar;
    }

    public int maximum() {
        return maxSoFar;
    }

    public float variance() {
        return (sum_sq / count) - sq(mean());
    }

    private static float sq(float mean) {
        return mean * mean;
    }

    public long sum() {
        return sum;
    }

    public float sumSq() {
        return sum_sq;
    }

    public long count() {
        return count;
    }

}
