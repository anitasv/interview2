package com.company.interview.expr;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Iterables {

    public static <T>
    Iterable<T> concat(Iterable<T> a, Iterable<T> b) {
        return new ConcatIterable<>(a, b);
    }

    public static <A, B, T>
    Iterable<T> cross(Iterable<A> a, Iterable<B> b, BiFunction<A, B, T> combine) {
        return new CrossIterable<>(a, b, combine);
    }

    public static <A, B>
    Iterable<B> map(Iterable<A> a, Function<A, B> mappingFunction) {
        return new MappingIterable<A, B>(a, mappingFunction);
    }

    public static <A, B>
    Iterable<B> flatmap(Iterable<A> a, Function<A, Iterable<B>> mappingFunction) {
        return new FlatMappingIterable<A, B>(a, mappingFunction);
    }

    public static
    Iterable<Integer> range(int a, int b) {
        return new RangeIterator(a, b);
    }
}
