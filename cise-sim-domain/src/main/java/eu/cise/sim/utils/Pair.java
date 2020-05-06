package eu.cise.sim.utils;

import java.io.Serializable;

public class Pair<A, B> implements Serializable {

    private static final long serialVersionUID = 7411480084023737566L;

    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }



}
