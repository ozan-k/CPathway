package com.ozank.cpathway.simulation;

public class Pair<T> {
    private final T name;
    private final int count;

    public Pair(T name, int count) {
        this.name = name;
        this.count = count;
    }

    public T getName() {
        return name;
    }
    public T getIndex() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "index=" + name +
                ", count=" + count +
                '}';
    }

}
