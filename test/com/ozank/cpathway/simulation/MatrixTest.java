package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    Matrix m;
    Matrix f;

    PairIndex p = new PairIndex(1,1);
    TripleIndex t = new TripleIndex(1,1,1);

    @BeforeEach
    public void init(){
        m = new Matrix<PairIndex>();
        f = new Matrix<TripleIndex>();
    }
    @Test
    public void incrementingANewCellSetsThatCellOne(){
        m.increment(p);
        f.increment(t);
        assertEquals(m.get(p),1);
        assertEquals(f.get(t),1);
    }

    @Test
    public void incrementingACellAddsOne(){
        m.increment(p);
        m.increment(p);
        f.increment(t);
        f.increment(t);
        assertEquals(m.get(p),2);
        assertEquals(f.get(t),2);
    }

    @Test
    public void decrementingACellWithOneTwiceThrowsException(){
        m.increment(p);
        m.decrement(p);
        f.increment(t);
        f.decrement(t);
        assertThrows(IllegalArgumentException.class,()-> m.decrement(p));
        assertThrows(IllegalArgumentException.class,()-> f.decrement(t));
    }

    @Test
    public void incrementingWithTwoNewPairsReturnsTwo(){
        PairIndex p1 = new PairIndex(1,1);
        TripleIndex t1 = new TripleIndex(1,1,1);
        m.increment(p);
        m.increment(p1);
        f.increment(t);
        f.increment(t1);
        assertEquals(2,m.get(new PairIndex(1,1)));
        assertEquals(2,f.get(new TripleIndex(1,1,1)));
    }

}