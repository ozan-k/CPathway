package com.ozank.cpathway.simulation;

public class Pair {
    private final String molecule;
    private final int count;

    public Pair(String molecule, int count) {
        this.molecule = molecule;
        this.count = count;
    }

    public String getMolecule() {
        return molecule;
    }

    public int getCount() {
        return count;
    }
}
