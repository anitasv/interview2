package com.company.interview.statistics;

/**
 * Tracks the statistics of a continual stream of values.
 */

public interface Statistic {

    /*
     * Called to update the statistic with a new sample value.
     */
    void event(int value);

    /*
     * Returns the mean of the received sample values.
     */
    float mean();

    /*
     * Returns the minimal received sample value.
     */
    int minimum();

    /*
     * Returns the maximal received sample value.
     */
    int maximum();

    /*
     * Returns an estimate of the variance of the total population
     * given the received sample values.
     */
    float variance();

}