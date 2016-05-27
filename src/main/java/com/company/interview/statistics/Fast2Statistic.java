package com.company.interview.statistics;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Fast2Statistic implements Statistic {

    private final Queue<SafeStatistic> nodes = new ConcurrentLinkedQueue<>();

    private final ThreadLocal<SafeStatistic> myNode = new ThreadLocal<SafeStatistic>() {

        @Override
        public SafeStatistic initialValue() {
            SafeStatistic safeStatistic = new SafeStatistic();
            nodes.add(safeStatistic);
            return safeStatistic;
        }
    };


    public void event(int value) {
        myNode.get().event(value);
    }

    public float mean() {
        long totalSum = 0;
        long totalCount = 0;
        for (SafeStatistic node : nodes) {
            totalSum += node.sum();
            totalCount += node.count();
        }
        return ((float) totalSum) / totalCount;
    }

    public int minimum() {
        int globMin = Integer.MAX_VALUE;
        for (SafeStatistic node : nodes) {
            int localMin = node.minimum();
            if (localMin < globMin) {
                globMin = localMin;
            }
        }
        return globMin;
    }

    public int maximum() {
        int globMax = Integer.MIN_VALUE;
        for (SafeStatistic node : nodes) {
            int localMax = node.maximum();
            if (localMax > globMax) {
                globMax = localMax;
            }
        }
        return globMax;
    }

    public float variance() {
        long totalSum = 0;
        long totalSumSq = 0;
        long totalCount = 0;
        for (SafeStatistic node : nodes) {
            totalSum += node.sum();
            totalSumSq += node.sumSq();
            totalCount += node.count();
        }
        float meanSq = ((float) totalSumSq) / totalCount;
        float mean = ((float) totalSum) / totalCount;
        return meanSq - mean * mean;
    }

}
