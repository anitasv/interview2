package com.company.interview.expr;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class LexicalCrossIterable<A, B, T> implements Iterable<T> {

    private final Iterable<A> a;
    private final Iterable<B> b;
    private final BiFunction<A, B, T> combine;

    public LexicalCrossIterable(Iterable<A> a, Iterable<B> b, BiFunction<A, B, T> combine) {
        this.a = a;
        this.b = b;
        this.combine = combine;
    }


    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private Iterator<A> aIter = a.iterator();

            private Iterator<B> bIter;

            private boolean hasNextA;

            private A nextA;

            private boolean hasNextB;

            private B nextB;

            private boolean isEnabled;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (!hasNextA) {
                        if (aIter.hasNext()) {
                            hasNextA = true;
                            nextA = aIter.next();
                            bIter = b.iterator();
                            isEnabled = false;
                        } else {
                            return false;
                        }
                    }

                    if (!hasNextB) {
                        if (bIter.hasNext()) {
                            hasNextB = true;
                            nextB = bIter.next();
                        } else {
                            hasNextA = false;
                            continue;
                        }
                    }

                    if (!isEnabled) {
                        if (nextB.toString().equals(nextA.toString())) {
                            isEnabled = true;
                        } else {
                            hasNextB = false;
                        }
                    } else {
                        return true;
                    }
                }
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T nextT = combine.apply(nextA, nextB);
                    hasNextB = false;
                    return nextT;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
