package com.company.interview.expr;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcatIterable<T> implements Iterable<T> {

    private final Iterable<T> a;

    private final Iterable<T> b;

    public ConcatIterable(Iterable<T> a, Iterable<T> b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Iterator<T> aIter;
            Iterator<T> bIter;

            private Iterator<T> aiter() {
                if (aIter == null) {
                    aIter = a.iterator();
                }
                return aIter;
            }

            private Iterator<T> biter() {
                if (bIter == null) {
                    bIter = b.iterator();
                }
                return bIter;
            }

            boolean first = true;


            @Override
            public boolean hasNext() {
                if (first) {
                    boolean aHasNext = aiter().hasNext();
                    if (aHasNext) {
                        return true;
                    } else {
                        first = false;
                    }
                }
                return biter().hasNext();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    if (first) {
                        return aiter().next();
                    } else {
                        return biter().next();
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
