package com.company.interview.expr;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static com.company.interview.expr.Iterables.*;

public class ExpressionSearch {

    interface Expr {
        double value();

        int size();

        StringBuilder toSB();
    }

    static class Terminal implements Expr {

        private final int val;

        private final int size;

        Terminal(int val, int size) {
            this.val = val;
            this.size = size;
        }


        @Override
        public double value() {
            return val;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public StringBuilder toSB() {
            return new StringBuilder().append(val);
        }

        public String toString() {
            return String.valueOf(val);
        }
    }

    static class UnaryExpression implements Expr {

        private final Expr operand;

        private final UnaryOperator<Double> op;

        private final double val;

        UnaryExpression(Expr operand, UnaryOperator<Double> op) {
            this.operand = operand;
            this.op = op;
            this.val = op.apply(operand.value());
        }

        @Override
        public double value() {
            return val;
        }

        @Override
        public int size() {
            return operand.size();
        }

        @Override
        public StringBuilder toSB() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(op.toString());
            builder.append(operand.toSB());
            builder.append(")");
            return builder;
        }

        @Override
        public String toString() {
            return toSB().toString();
        }
    }

    static class BinaryExpression implements Expr {

        private final Expr left;

        private final Expr right;

        private final BinaryOperator<Double> op;

        private final double val;

        BinaryExpression(Expr left, Expr right, BinaryOperator<Double> op) {
            this.left = left;
            this.right = right;
            this.op = op;
            this.val = op.apply(left.value(), right.value());
        }


        @Override
        public double value() {
            return val;
        }

        @Override
        public int size() {
            return left.size() + right.size();
        }

        @Override
        public String toString() {
            return toSB().toString();
        }

        @Override
        public StringBuilder toSB() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(left.toSB());
            builder.append(" ");
            builder.append(op.toString());
            builder.append(" ");
            builder.append(right.toSB());
            builder.append(")");
            return builder;
        }
    }

    private static final BinaryOperator<Double> PLUS = new BinaryOperator<Double>() {
        @Override
        public String toString() {
            return "+";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a + b;
        }
    };

    private static final BinaryOperator<Double> TIMES = new BinaryOperator<Double>() {
        @Override
        public String toString() {
            return "*";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a * b;
        }
    };

    private static final BinaryOperator<Double> MINUS = new BinaryOperator<Double>() {
        @Override
        public String toString() {
            return "-";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a - b;
        }
    };

    private static final BinaryOperator<Double> DIVIDE = new BinaryOperator<Double>() {
        @Override
        public String toString() {
            return "/";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a / b;
        }
    };

    public static final UnaryOperator<Double> ADDITIVE_INVERSE = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double a) {
            return -a;
        }

        @Override
        public String toString() {
            return "-";
        }
    };

    public static final UnaryOperator<Double> MULTIPLICATIVE_INVERSE = new UnaryOperator<Double>() {
        @Override
        public Double apply(Double a) {
            return 1.0 / a;
        }

        @Override
        public String toString() {
            return "1/";
        }
    };


    private static final Map<Integer, List<Expr>> leafDp = new HashMap<>();

    public static List<Expr> terminal(int len) {
        return leafDp.computeIfAbsent(len, x -> {
            int nines = 0;
            for (int i = 0; i < len; i++) {
                nines *= 10;
                nines += 9;
            }
            List<Expr> list = new ArrayList<>();
            list.add(new Terminal(nines, len));
            return Collections.unmodifiableList(list);
        });
    }

    public static Iterable<Expr> deep(int len,
                                      BinaryOperator<Double> op,
                                      UnaryOperator<Double> inverse,
                                      BinaryOperator<Double> partialInverse) {
        return flatmap(range(1, 1 + len / 2), (i) -> {
            Iterable<Expr> ret = Collections.emptyList();

            int j = len - i;
            Iterable<Expr> leftCandidates = generate(i, op);
            Iterable<Expr> rightCandidates = generate(j, null);

            if (i != j) {
                Iterable<Expr> result1 = cross(leftCandidates, rightCandidates, (e1, e2) -> new BinaryExpression(e1, e2, op));

                ret = concat(ret, result1);

                Iterable<Expr> result2 = cross(rightCandidates, leftCandidates, (e1, e2) -> new BinaryExpression(e1, e2, partialInverse));

                ret = concat(ret, result2);

                Iterable<Expr> result3 = cross(leftCandidates, rightCandidates, (e1, e2) -> new BinaryExpression(e1, e2, partialInverse));

                ret = concat(ret, result3);

            } else {
                Iterable<Expr> result1 = lexicalCross(leftCandidates, rightCandidates, (e1, e2) -> new BinaryExpression(e1, e2, op));

                ret = concat(ret, result1);

                Iterable<Expr> result2 = lexicalCross(leftCandidates, rightCandidates, (e1, e2) -> new BinaryExpression(e2, e1, partialInverse));

                ret = concat(ret, result2);

                Iterable<Expr> result3 = lexicalCrossSkip(leftCandidates, rightCandidates, (e1, e2) -> new BinaryExpression(e1, e2, partialInverse));

                ret = concat(ret, result3);
            }

            return ret;
        });
    }

    public static Iterable<Expr> generate(int len, BinaryOperator<Double> ban) {

        Iterable<Expr> ret = Collections.emptyList();

        ret = concat(ret, terminal(len));

        if (PLUS != ban) {
            ret = concat(ret, deep(len, PLUS, ADDITIVE_INVERSE, MINUS));
        }
        if (TIMES != ban) {
            ret = concat(ret, deep(len, TIMES, MULTIPLICATIVE_INVERSE, DIVIDE));
        }

        return ret;
    }

    public static void main(String[] args) {

//        for (int i = 1; i < 5; i++) {
//            for (Expr expr : generate(i, null)) {
//                System.out.println(i + "\t" + expr.value() + "\t" + expr);
//            }
//        }

        Iterable<Expr> solns = flatmap(Iterables.range(1, 100), (n) -> generate(n, null));

        Map<Integer, Expr> hasFound = new HashMap<>();
        long startTime = System.nanoTime();

        int located = 0;
        int maxSoFar = 0;
        for (Expr expr : solns) {
            double val = expr.value();
            if (val == Math.floor(val)) {
                hasFound.put((int) val, expr);

                while (true) {
                    if (hasFound.containsKey(located + 1)) {
                        Expr match = hasFound.get(located + 1);
                        if (match.size() > maxSoFar) {
                            maxSoFar = match.size();
                            long endTime = System.nanoTime();
                            long duration = endTime - startTime;
                            long seconds = TimeUnit.NANOSECONDS.toSeconds(duration);
                            System.out.println((located + 1) + "\t" + match.size() + "\t" + match + "\t" + seconds + " s");
                        }
                        located++;
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
