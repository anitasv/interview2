package com.company.interview.expr;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class FlatMappingIterable<A, B> implements Iterable<B> {

    private final Iterable<A> a;

    private final Function<A, Iterable<B>> mappingFunction;

    public FlatMappingIterable(Iterable<A> a, Function<A, Iterable<B>> mappingFunction) {
        this.a = a;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public Iterator<B> iterator() {

        return new Iterator<B>() {

            private Iterator<A> aIter = a.iterator();

            private Iterator<B> bIter;

            private boolean hasNextA;

            private A nextA;

            private boolean hasNextB;

            private B nextB;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (!hasNextA) {
                        if (aIter.hasNext()) {
                            hasNextA = true;
                            nextA = aIter.next();
                            bIter = mappingFunction.apply(nextA).iterator();
                            hasNextB = false;
                        } else {
                            return false;
                        }
                    }

                    if (!hasNextB) {
                        if (bIter.hasNext()) {
                            hasNextB = true;
                            nextB = bIter.next();
                            return true;
                        } else {
                            hasNextA = false;
                        }
                    } else {
                        return true;
                    }
                }
            }

            @Override
            public B next() {
                if (hasNext()) {
                    hasNextB = false;
                    return nextB;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
