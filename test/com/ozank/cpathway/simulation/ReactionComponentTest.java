package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReactionComponentTest {
    // Predator -> 0
    // Prey -> 1
    ReactionComponent left1 = new ReactionComponent(Set.of(new Pair<>(0,1),
                                                            new Pair<>(1,1)));
    ReactionComponent right1 = new ReactionComponent(Set.of(new Pair<>(0,2)));
    ReactionComponent left2 = new ReactionComponent(Set.of(new Pair<>(0,1)));
    ReactionComponent right2 = new ReactionComponent();
    ReactionComponent left3 = new ReactionComponent(Set.of(new Pair<>(1,1)));
    ReactionComponent right3 = new ReactionComponent(Set.of(new Pair<>(1,2)));


    @Test
    public void includesAllComponents(){
        assertTrue(left1.containsKey(0));
        assertTrue(left1.containsKey(1));
    }

    @Test
    public void DoesNotContainNonComponents(){
        assertFalse(left1.containsKey(2));
        assertFalse(left1.containsKey(3));
    }

    @Test
    public void ContainsAllTheKeys(){
        assertTrue(left1.getReactionComponents().containsKey(0));
        assertTrue(left1.getReactionComponents().containsKey(1));
        assertEquals(left1.getReactionComponents().size(),2);

    }
}