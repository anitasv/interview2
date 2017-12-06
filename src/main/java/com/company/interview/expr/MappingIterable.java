package com.company.interview.expr;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MappingIterable<A, B> implements Iterable<B> {

    private final Iterable<A> a;
    private final Function<A, B> mappingFunction;

    public MappingIterable(Iterable<A> a, Function<A, B> mappingFunction) {
        this.a = a;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public Iterator<B> iterator() {
        return new Iterator<B>() {

            private final Iterator<A> internal = a.iterator();

            @Override
            public boolean hasNext() {
                return internal.hasNext();
            }

            @Override
            public B next() {
                if (hasNext()) {
                    return mappingFunction.apply(internal.next());
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
