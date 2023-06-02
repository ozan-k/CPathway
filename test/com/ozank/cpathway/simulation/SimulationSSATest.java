package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SimulationSSATest {

    public Map<String,Integer> reactionComponentFactory(Set<Pair> list){
        Map<String,Integer> result = new HashMap<>();
        for (Pair p : list){
            result.put(p.getMolecule(),p.getCount());
        }
        return result;
    }
    double rate1 = 1.0;
    Map<String,Integer> left1R = reactionComponentFactory(Set.of(new Pair("Predator",1), new Pair("Prey",1)));
    Map<String,Integer> right1R = reactionComponentFactory(Set.of(new Pair("Predator",2)));

    @Test
    public void testReactionComponent(){
        for (String s : left1R.keySet()){
            System.out.println(s + " : " +  left1R.get(s));
        }
    }


    double rate2 = 100;

    Map<String,Integer> left2R = reactionComponentFactory(Set.of(new Pair("Predator",1)));
    Map<String,Integer> right2R = new HashMap<>();

    double rate3 = 300;
    Map<String,Integer> left3R = reactionComponentFactory(Set.of(new Pair("Prey",1)));
    Map<String,Integer> right3R = reactionComponentFactory(Set.of(new Pair("Prey",2)));

    Reaction r1 = new Reaction(rate1,left1R,right1R);
    Reaction r2 = new Reaction(rate2,left2R,right2R);
    Reaction r3 = new Reaction(rate3,left3R,right3R);

    List<Reaction> reactions1 = List.of(r1,r2,r3);
    Map<String,Integer> initState1 =  reactionComponentFactory(Set.of(
            new Pair("Predator",100),
            new Pair("Prey",100)));

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void printModel(){
        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
        simulationModel.printState();
        simulationModel.printReactions();
        simulationModel.printDependencies();
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.printPropensities();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void simulationTest1(){
        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.simulateWithStepNumber(20000);
        simulation.writeToFile("sim1.csv");
        // simulation.printTrajectory();
    }

    @Test
    public void simulationTest1b(){
        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.simulateWithTimeLimit(0.25);
        simulation.writeToFile("sim1bb.csv");
        // simulation.printTrajectory();
        simulationModel.printReactions();
        simulation.printFluxes();
    }

    @Test
    public void simulationTest1c(){
        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.simulateWithTimeLimit(0.25);
        simulation.printFluxes();
        System.out.println();
        SimulationSSA simulation1 = new SimulationSSA(simulationModel);
        simulation1.simulateWithTimeLimit(0.25);
        simulation1.printFluxes();
    }


    @Test
    public void testMoleculesList(){
        SimulationModel model = new SimulationModel(reactions1,initState1);
        assertEquals(List.of("Predator","Prey"),model.getMoleculesList());
    }

    @Test
    public void testSpeciesMap(){
        SimulationModel model = new SimulationModel(reactions1,initState1);
        Map<String,Integer> speciesMap = model.getSpeciesMap();
        for (String s : speciesMap.keySet()){
            System.out.println(s  + " : " + speciesMap.get(s));
        }
        assertEquals(0,speciesMap.get("Predator"));
        assertEquals(1,speciesMap.get("Prey"));
    }

    // ====================================================

    double rate4 = 1;
    Map<String,Integer> left4R = reactionComponentFactory(Set.of(new Pair("S1",1),new Pair("S2",1)));
    Map<String,Integer> right4R = reactionComponentFactory(Set.of(new Pair("S3",1)));

    double rate5 = 1;
    Map<String,Integer> left5R = reactionComponentFactory(Set.of(new Pair("S3",1)));
    Map<String,Integer> right5R = reactionComponentFactory(Set.of(new Pair("S1",1),new Pair("S2",1)));

    double rate6 = 0.1;
    Map<String,Integer> left6R = reactionComponentFactory(Set.of(new Pair("S3",1)));
    Map<String,Integer> right6R = reactionComponentFactory(Set.of(new Pair("S1",1),new Pair("S5",1)));


    double rate7 = 1;
    Map<String,Integer> left7R = reactionComponentFactory(Set.of(new Pair("S4",1),new Pair("S5",1)));
    Map<String,Integer> right7R = reactionComponentFactory(Set.of(new Pair("S6",1)));

    double rate8 = 1;
    Map<String,Integer> left8R = reactionComponentFactory(Set.of(new Pair("S6",1)));
    Map<String,Integer> right8R = reactionComponentFactory(Set.of(new Pair("S4",1),new Pair("S5",1)));

    double rate9 = 0.1;
    Map<String,Integer> left9R = reactionComponentFactory(Set.of(new Pair("S6",1)));
    Map<String,Integer> right9R = reactionComponentFactory(Set.of(new Pair("S4",1),new Pair("S2",1)));

    Reaction r4 = new Reaction(rate4,left4R,right4R);
    Reaction r5 = new Reaction(rate5,left5R,right5R);
    Reaction r6 = new Reaction(rate6,left6R,right6R);
    Reaction r7 = new Reaction(rate7,left7R,right7R);
    Reaction r8 = new Reaction(rate8,left8R,right8R);
    Reaction r9 = new Reaction(rate9,left9R,right9R);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    List<Reaction> reactions2 = List.of(r4,r5,r6,r7,r8,r9);
    Map<String,Integer> initState2 =  reactionComponentFactory(Set.of(
            new Pair("S1",1),
            new Pair("S2",50),
            new Pair("S4",1),
            new Pair("S5",50)));
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void simulationTest2(){
        SimulationModel simulationModel = new SimulationModel(reactions2,initState2);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulationModel.printReactions();
        simulation.printSimulationState();
        simulation.simulateWithStepNumber(1);
        simulation.printSimulationState();
        simulation.simulateWithStepNumber(1);
        simulation.printSimulationState();
        simulation.writeToFile("sim2.csv");
        // simulation.printTrajectory();
    }

    @Test
    public void simulationTest2b(){
        SimulationModel simulationModel = new SimulationModel(reactions2,initState2);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.simulateWithTimeLimit(1000);
        simulation.writeToFile("sim2b.csv");
        // simulation.printTrajectory();
    }

    // ==================================================

    double  concentrationD = 50;
    double concentrationT = 500;
    double rate11 = 1.0;
    Map<String,Integer> left11R = reactionComponentFactory(Set.of(new Pair("A",1),new Pair("R",1)));
    Map<String,Integer> right11R = reactionComponentFactory(Set.of(new Pair("RA",1)));

    double rate12 = 1.0;
    Map<String,Integer> left12R = reactionComponentFactory(Set.of(new Pair("A",1),new Pair("RD",1)));
    Map<String,Integer> right12R = reactionComponentFactory(Set.of(new Pair("RDA",1)));

    double rate13 = 1.0;
    Map<String,Integer> left13R = reactionComponentFactory(Set.of(new Pair("A",1),new Pair("RT",1)));
    Map<String,Integer> right13R = reactionComponentFactory(Set.of(new Pair("RTA",1)));

    double rate14 = 0.43;
    Map<String,Integer> left14R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("R",1)));
    Map<String,Integer> right14R = reactionComponentFactory(Set.of(new Pair("RE",1)));

    double rate15 = 0.0054;
    Map<String,Integer> left15R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("RD",1)));
    Map<String,Integer> right15R = reactionComponentFactory(Set.of(new Pair("RDE",1)));

    double rate16 = 0.0075;
    Map<String,Integer> left16R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("RT",1)));
    Map<String,Integer> right16R = reactionComponentFactory(Set.of(new Pair("RTE",1)));

    double rate17 = 0.033 * concentrationD;
    Map<String,Integer> left17R = reactionComponentFactory(Set.of(new Pair("R",1)));
    Map<String,Integer> right17R = reactionComponentFactory(Set.of(new Pair("RD",1)));

    double rate18 = 0.1 * concentrationT;
    Map<String,Integer> left18R = reactionComponentFactory(Set.of(new Pair("R",1)));
    Map<String,Integer> right18R = reactionComponentFactory(Set.of(new Pair("RT",1)));


    double rate19 = 500.0;
    Map<String,Integer> left19R = reactionComponentFactory(Set.of(new Pair("RA",1)));
    Map<String,Integer> right19R = reactionComponentFactory(Set.of(new Pair("A",1),new Pair("R",1)));

    double rate20 = 0.02;
    Map<String,Integer> left20R = reactionComponentFactory(Set.of(new Pair("RD",1)));
    Map<String,Integer> right20R = reactionComponentFactory(Set.of(new Pair("R",1)));

    double rate21 = 500.0;
    Map<String,Integer> left21R = reactionComponentFactory(Set.of(new Pair("RDA",1)));
    Map<String,Integer> right21R = reactionComponentFactory(Set.of(new Pair("A",1),new Pair("RD",1)));

    double rate22 = 0.136;
    Map<String,Integer> left22R = reactionComponentFactory(Set.of(new Pair("RDE",1)));
    Map<String,Integer> right22R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("RD",1)));

    double rate23 = 6.0;
    Map<String,Integer> left23R = reactionComponentFactory(Set.of(new Pair("RDE",1)));
    Map<String,Integer> right23R = reactionComponentFactory(Set.of(new Pair("RE",1)));

    double rate24 = 1.074;
    Map<String,Integer> left24R = reactionComponentFactory(Set.of(new Pair("RE",1)));
    Map<String,Integer> right24R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("R",1)));

    double rate25 = 0.033 * concentrationD;
    Map<String,Integer> left25R = reactionComponentFactory(Set.of(new Pair("RE",1)));
    Map<String,Integer> right25R = reactionComponentFactory(Set.of(new Pair("RDE",1)));

    double rate26 = 0.1 * concentrationT;
    Map<String,Integer> left26R = reactionComponentFactory(Set.of(new Pair("RE",1)));
    Map<String,Integer> right26R = reactionComponentFactory(Set.of(new Pair("RTE",1)));

    double rate27 = 0.02;
    Map<String,Integer> left27R = reactionComponentFactory(Set.of(new Pair("RT",1)));
    Map<String,Integer> right27R = reactionComponentFactory(Set.of(new Pair("R",1)));

    double rate28 = 0.02;
    Map<String,Integer> left28R = reactionComponentFactory(Set.of(new Pair("RT",1)));
    Map<String,Integer> right28R = reactionComponentFactory(Set.of(new Pair("RD",1)));

    double rate29 = 3.0;
    Map<String,Integer> left29R = reactionComponentFactory(Set.of(new Pair("RTA",1)));
    Map<String,Integer> right29R = reactionComponentFactory(Set.of(new Pair("A",1), new Pair("RT",1)));

    double rate30 = 2104.0;
    Map<String,Integer> left30R = reactionComponentFactory(Set.of(new Pair("RTA",1)));
    Map<String,Integer> right30R = reactionComponentFactory(Set.of(new Pair("RDA",1)));

    double rate31 = 76.8;
    Map<String,Integer> left31R = reactionComponentFactory(Set.of(new Pair("RTE",1)));
    Map<String,Integer> right31R = reactionComponentFactory(Set.of(new Pair("E",1),new Pair("RT",1)));

    double rate32 = 0.02;
    Map<String,Integer> left32R = reactionComponentFactory(Set.of(new Pair("RTE",1)));
    Map<String,Integer> right32R = reactionComponentFactory(Set.of(new Pair("RDE",1)));

    double rate33 = 0.02;
    Map<String,Integer> left33R = reactionComponentFactory(Set.of(new Pair("RTE",1)));
    Map<String,Integer> right33R = reactionComponentFactory(Set.of(new Pair("RE",1)));

    Reaction r11 = new Reaction(rate11,left11R,right11R);
    Reaction r12 = new Reaction(rate12,left12R,right12R);
    Reaction r13 = new Reaction(rate13,left13R,right13R);
    Reaction r14 = new Reaction(rate14,left14R,right14R);
    Reaction r15 = new Reaction(rate15,left15R,right15R);
    Reaction r16 = new Reaction(rate16,left16R,right16R);
    Reaction r17 = new Reaction(rate17,left17R,right17R);
    Reaction r18 = new Reaction(rate18,left18R,right18R);
    Reaction r19 = new Reaction(rate19,left19R,right19R);
    Reaction r20 = new Reaction(rate20,left20R,right20R);
    Reaction r21 = new Reaction(rate21,left21R,right21R);
    Reaction r22 = new Reaction(rate22,left22R,right22R);
    Reaction r23 = new Reaction(rate23,left23R,right23R);
    Reaction r24 = new Reaction(rate24,left24R,right24R);
    Reaction r25 = new Reaction(rate25,left25R,right25R);
    Reaction r26 = new Reaction(rate26,left26R,right26R);
    Reaction r27 = new Reaction(rate27,left27R,right27R);
    Reaction r28 = new Reaction(rate28,left28R,right28R);
    Reaction r29 = new Reaction(rate29,left29R,right29R);
    Reaction r30 = new Reaction(rate30,left30R,right30R);
    Reaction r31 = new Reaction(rate31,left31R,right31R);
    Reaction r32 = new Reaction(rate32,left32R,right32R);
    Reaction r33 = new Reaction(rate33,left33R,right33R);

    List<Reaction> reactions3 = List.of(r11,r12,r13,r14,r15,r16,r17,r18,r19,r20,
            r21,r22,r23,r24,r25,r26,r27,r28,r29,r30,r31,r32,r33);
    Map<String,Integer> initState3 =  reactionComponentFactory(Set.of(
            new Pair("A",10),
            new Pair("R",1000),
            new Pair("E",776)));

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void combinatorialTest(){
        assertEquals(0, SimulationSSA.combinatorial(0,1));
        assertEquals(3, SimulationSSA.combinatorial(3,1));
        assertEquals(3, SimulationSSA.combinatorial(3,2));
        assertEquals(6, SimulationSSA.combinatorial(4,2));
        assertEquals(1, SimulationSSA.combinatorial(1,1));
        assertEquals(1, SimulationSSA.combinatorial(3,3));
    }

//    @Test
//    public void testNextReaction(){
//        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
//        simulationModel.printState();
//        simulationModel.printReactions();
//        simulationModel.printDependencies();
//        int[] results = new int[4];
//
//        IntStream.range(0,100000).forEach(x -> {
//            //results[simulation.tester()]++;
//        });
//        IntStream.range(0,4).forEach(x -> System.out.println(x + " : " + results[x]));
//    }

//    @Test
//    public void logTest(){
//        SimulationModel simulationModel = new SimulationModel(reactions,initState);
//        Simulation simulation = new Simulation(simulationModel);
//        //System.out.println(Math.log(2.718281828459045));
//        System.out.println(IntStream.range(0,10000)
//                .asDoubleStream()
//                .map(x -> simulation.tester()).average());
//    }

    @Test
    public void matrixMTest(){
        SimulationModel simulationModel = new SimulationModel(reactions1,initState1);
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        Matrix<PairIndex> m = simulation.getM();
        for (PairIndex p : m.keySet()){
            System.out.println(p + " : " + m.get(p));
        }
    }

    @Test
    public void simulationTest3(){
        SimulationModel simulationModel = new SimulationModel(reactions3,initState3);
        simulationModel.printReactions();
        SimulationSSA simulation = new SimulationSSA(simulationModel);
        simulation.simulateWithTimeLimit(1.8);
        simulation.writeToFile("sim3.csv");
        simulationModel.printState();
        simulation.printFluxes();
    }

    @Test
    public void nextIntTest(){
        Random r = new Random();
        IntStream.range(0,100)
                        .forEach(x->
        System.out.print(r.nextInt(2)+ " "));
    }


    @Test
    public void arrayTest(){
        int[] a = {1,2};
        System.out.println(a[0] + " " + a[1]);
        Map<String,Integer> m = new HashMap<>();
        m.put("0",a[0]);
        int x = a[1];
        m.put("1",a[1]);
        a[1] = 3;
        System.out.println(m.get("1"));
    }


    @Test
    public void union(){
        Set<Integer> s1 = Set.of(1,2,3);
        Set<Integer> s2 = Set.of(2,5);

        List<Set<Integer>> setList = List.of(s1,s2);

        Set<Integer> set = setList.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        System.out.println(set);

    }

//    @Test
//    public void resourceSourceReactionTest(){
//        SimulationModel simulationModel = new SimulationModel(reactions,initState);
//        Simulation simulation = new Simulation(simulationModel);
//        simulation.tester();
//        int[] result = new int[4];
//        IntStream.range(0,1000000)
//                        .forEach(x-> {
//                            result[simulation.getReactionOrigin(0)]++;
//                        });
//        IntStream.range(0,4)
//                .forEach(x-> System.out.println(x + " : " + result[x]));
//
//    }

}