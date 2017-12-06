package com.company.interview.expr;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by anita on 12/7/2017.
 */
public class RangeIterator implements Iterable<Integer> {

    private final int a;

    private final int b;

    public RangeIterator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            int pos = a;

            @Override
            public boolean hasNext() {
                return pos < b;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return pos++;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
