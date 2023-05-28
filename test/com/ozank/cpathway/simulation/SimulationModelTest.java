package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationModelTest {
    double rate1 = 1.0;
    Set<Pair<String>> left1R = Set.of(new Pair<>("Predator",1), new Pair<>("Prey",1));
    Set<Pair<String>> right1R = Set.of(new Pair<>("Predator",2));

    double rate2 = 100;

    Set<Pair<String>> left2R = Set.of(new Pair<String>("Predator",1));
    Set<Pair<String>> right2R = new HashSet<>();

    double rate3 = 300;
    Set<Pair<String>> left3R = Set.of(new Pair<>("Prey",1));
    Set<Pair<String>> right3R = Set.of(new Pair<>("Prey",2));

    Reaction r1 = new Reaction(rate1,left1R,right1R);
    Reaction r2 = new Reaction(rate2,left2R,right2R);
    Reaction r3 = new Reaction(rate3,left3R,right3R);


    Set<Pair<String>> left4R = Set.of(new Pair<>("A",1),new Pair<>("T",2));
    Set<Pair<String>> right4R = Set.of(new Pair<>("D",1),new Pair<>("Z",2));

    Reaction r4 = new Reaction(1.0,left4R,right4R);

    List<Reaction> reactions = List.of(r1, r2, r3);
    Set<Pair<String>> initState = Set.of(
            new Pair<>("Predator",100),
            new Pair<>("Prey",100));

    @Test
    public void printModel(){
        SimulationModel simulationModel = new SimulationModel(reactions,initState);
        simulationModel.printState();
        System.out.println();
        simulationModel.printReactions();
        System.out.println();
        simulationModel.printDependencies();

    }
}

//    Set<StoicPair> left1 = Set.of(new StoicPair(0,1), new StoicPair(1,1));
//    Set<StoicPair> right1 = Set.of(new StoicPair(0,2));

//    Set<StoicPair> left2 = Set.of(new StoicPair(0,1));
//    Set<StoicPair> right2 = new HashSet<>();

//    Set<StoicPair> left3 = Set.of(new StoicPair(1,1));
//    Set<StoicPair> right3 = Set.of(new StoicPair(1,2));

//    Set<ReactantPair> left4R = Set.of(new ReactantPair("A",1),new ReactantPair("T",2));
//    Set<ReactantPair> right4R = Set.of(new ReactantPair("D",1),new ReactantPair("Z",2));
//    Reaction r4 = new Reaction(1.0,left4R,right4R);

//    Set<Reaction> reactions = Set.of(r1,r2,r3,r4);
//    Set<ReactantPair> initState = Set.of(
//            new ReactantPair("Predator",100),
//            new ReactantPair("Prey",100),
//            new ReactantPair("C",5),new ReactantPair("T",100));

//    @Test
//    public void returnsTheSpeciesSet() {
//        System.out.println(SimulationModel.getReactionComponentSpecies(left1R));
//    }

//    @Test
//    public void returnsACorrectStringMap(){
//        List<String> moleculesList = SimulationModel.getMoleculesList(reactions,initState);
//        Map<String,Integer> map = SimulationModel.getSpeciesMap(moleculesList);
//        for (String s : map.keySet()){
//            System.out.println(s + " " + map.get(s));
//        };
//        assertEquals(0,map.get("A"));
//        assertEquals(3,map.get("Predator"));
//
//    }
