package com.company.interview.statistics;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StandardTest {

    public void check(Statistic statistic) {

        int N = 2;

        for (int i = 1; i <= N; i++) {
            statistic.event(i);
        }

        float mean = (float) (N + 1) / 2;
        float mean_sq = (float) (N + 1) * (2 * N + 1) / 6;
        float variance = mean_sq - mean * mean;

        assertEquals(statistic.minimum(), 1);
        assertEquals(statistic.maximum(), N);
        assertEquals(statistic.mean(), mean);
        assertEquals(statistic.variance(), variance);
    }

    @Test
    public void testSync() {
        check(new SynchStatistic());
    }

    @Test
    public void testCas() {
        check(new CasStatistic());
    }

    @Test
    public void testFast() {
        check(new FastStatistic());
    }

    @Test
    public void testUnsafe() {
        check(new UnsafeStatistic());
    }

    @Test
    public void testAcc() {
        check(new AccStatistic());
    }

    @Test
    public void testAcc2() {
        check(new Acc2Statistic());
    }

    @Test
    public void testSafe() {
        check(new SafeStatistic());
    }

    @Test
    public void testFast2() {
        check(new Fast2Statistic());
    }
}
