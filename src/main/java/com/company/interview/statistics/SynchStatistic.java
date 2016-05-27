package com.company.interview.statistics;


public class SynchStatistic implements Statistic {

    private final Object monitor = new Object();

    private int minSoFar = Integer.MAX_VALUE;
    private int maxSoFar = Integer.MIN_VALUE;

    private long sum = 0;

    private long sum_sq = 0;

    private int count = 0;

    public void event(int value) {
        synchronized (monitor) {
            sum += value;
            sum_sq += ((long)value) * value;
            count++;
            if (value < minSoFar) {
                minSoFar = value;
            }
            if (value > maxSoFar) {
                maxSoFar = value;
            }
        }
    }

    public float mean() {
        synchronized (monitor) {
            return (float) sum / count;
        }
    }

    public int minimum() {
        synchronized (monitor) {
            return minSoFar;
        }
    }

    public int maximum() {
        synchronized (monitor) {
            return maxSoFar;
        }
    }

    public float variance() {
        synchronized (monitor) {
            return (((float) sum_sq) / count) - sq(mean());
        }
    }

    private static float sq(float mean) {
        return mean * mean;
    }
}
