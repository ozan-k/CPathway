package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairIndexTest {
    @Test
    public void returnsTrueWithEquals(){
        PairIndex p1 = new PairIndex(1,2);
        PairIndex p2 = new PairIndex(1,1+1);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void returnsFalseWithDifferentObject(){
        PairIndex p1 = new PairIndex(1,2);
        Integer p2 = 2;
        assertFalse(p1.equals(p2));
    }

    @Test
    public void returnsFalseWithDifferentPair(){
        PairIndex p1 = new PairIndex(1,2);
        PairIndex p2 = new PairIndex(3,7);
        assertFalse(p1.equals(p2));
    }
}