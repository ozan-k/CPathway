package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripleIndexTest {
    @Test
    public void incrementingANewCellSetsThatCellOne() {
        Matrix m = new Matrix<TripleIndex>();
        TripleIndex t = new TripleIndex(1, 1,1);
        m.increment(t);
        assertEquals(m.get(t), 1);
    }

    @Test
    public void incrementingACellAddsOne() {
        Matrix m = new Matrix<TripleIndex>();
        TripleIndex t = new TripleIndex(1, 1,1);
        m.increment(t);
        m.increment(t);
        assertEquals(m.get(t), 2);
    }

    @Test
    public void decrementingACellWithOneTwiceThrowsException() {
        Matrix m = new Matrix<TripleIndex>();
        TripleIndex t = new TripleIndex(1, 1,1);
        m.increment(t);
        m.decrement(t);
        assertThrows(IllegalArgumentException.class, () -> m.decrement(t));
    }
}