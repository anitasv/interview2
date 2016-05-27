package com.company.interview.statistics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class PerfTest {

    public static void main(String[] args) throws InterruptedException {

        Executor executor = ForkJoinPool.commonPool();

        loadTest(executor, new CasStatistic());
        loadTest(executor, new SynchStatistic());
        loadTest(executor, new AccStatistic());
        loadTest(executor, new FastStatistic());
        loadTest(executor, new UnsafeStatistic());
        loadTest(executor, new SafeStatistic());
        loadTest(executor, new Acc2Statistic());
        loadTest(executor, new Fast2Statistic());
    }

    public static void loadTest(Executor executor, Statistic statistic) throws InterruptedException {
        long startTime = System.nanoTime();
        int concurrency = Runtime.getRuntime().availableProcessors();
        CountDownLatch countDownLatch = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {

            executor.execute(() -> {
                int k = 0;
                for (int j = 0; j < 1000000; j++) {
                    k = k * 1103515245 + 12345;
                    statistic.event(k % 1000);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        float mean = statistic.mean();
        float min = statistic.minimum();
        float max = statistic.maximum();
        float variance = statistic.variance();
        long endTime = System.nanoTime();
        System.out.println("Under Test: " + statistic.getClass());
        System.out.println("" + mean + " (" + min + ", " + max + "); " + variance);
        System.out.println("Time Taken: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
    }
}
