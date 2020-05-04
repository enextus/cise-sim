package eu.cise.sim.utils;

import java.io.Serializable;

public class Pair<A, B> implements Serializable {

    private static final long serialVersionUID = 42L;

    private A a; //Acknowledge
    private B b; //message Body

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    Pair() {
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }


}
