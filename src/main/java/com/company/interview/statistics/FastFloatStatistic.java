package com.company.interview.statistics;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FastFloatStatistic  implements Statistic {

    private final Queue<FloatStatistic> nodes = new ConcurrentLinkedQueue<>();

    private final ThreadLocal<FloatStatistic> myNode = new ThreadLocal<FloatStatistic>() {

        @Override
        public FloatStatistic initialValue() {
            FloatStatistic floatStatistic = new FloatStatistic();
            nodes.add(floatStatistic);
            return floatStatistic;
        }
    };


    public void event(int value) {
        myNode.get().event(value);
    }

    public float mean() {
        long totalSum = 0;
        long totalCount = 0;
        for (FloatStatistic node : nodes) {
            totalSum += node.sum();
            totalCount += node.count();
        }
        return ((float) totalSum) / totalCount;
    }

    public int minimum() {
        int globMin = Integer.MAX_VALUE;
        for (FloatStatistic node : nodes) {
            int localMin = node.minimum();
            if (localMin < globMin) {
                globMin = localMin;
            }
        }
        return globMin;
    }

    public int maximum() {
        int globMax = Integer.MIN_VALUE;
        for (FloatStatistic node : nodes) {
            int localMax = node.maximum();
            if (localMax > globMax) {
                globMax = localMax;
            }
        }
        return globMax;
    }

    public float variance() {
        long totalSum = 0;
        float totalSumSq = 0;
        long totalCount = 0;
        for (FloatStatistic node : nodes) {
            totalSum += node.sum();
            totalSumSq += node.sumSq();
            totalCount += node.count();
        }
        float meanSq = totalSumSq / totalCount;
        float mean = ((float) totalSum) / totalCount;
        return meanSq - mean * mean;
    }

}
