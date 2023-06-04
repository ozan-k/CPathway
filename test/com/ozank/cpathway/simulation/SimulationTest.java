package com.ozank.cpathway.simulation;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimulationTest {
    @Test
    public void setTest(){
        Set<String> set1 = Set.of("R-HSA-5205670","R-HSA-5205671","R-HSA-1252240","R-HSA-201579","R-HSA-5205679",
                "R-HSA-113595","R-HSA-5205659 ","R-HSA-992714","R-HSA-193937","R-HSA-5205671 ","R-HSA-5205637",
                "R-HSA-5205638","R-HSA-992745","R-HSA-5205630","R-HSA-5205668","R-HSA-1267988","R-HSA-5205624",
                "R-HSA-5205653","R-HSA-5205658","R-HSA-5205659","R-HSA-5205683","R-HSA-5205688");
        System.out.println(set1.size());
    }

    public Map<String,Integer> reactionComponentFactory(Set<Pair> list){
        Map<String,Integer> result = new HashMap<>();
        for (Pair p : list){
            result.put(p.getMolecule(),p.getCount());
        }
        return result;
    }
    String name1 = "R-HSA-5205649";
    // ["R-HSA-5205658",1] + ["R-HSA-5205659",1]
    // -->
    // ["R-HSA-5205670",1], 1.0
    Map<String,Integer> leftR1 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205658",1),
            new Pair("R-HSA-5205659",1)
            ));
    Map<String,Integer> rightR1 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205670",1)
    ));
    double rate1 = 1.0;

    Reaction r1 = new Reaction(name1,rate1,leftR1,rightR1);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    String name2 = "R-HSA-5205681";
    // [R-HSA-5205671,1]
    // -->
    // [R-HSA-5205628,1] , 1.0

    Map<String,Integer> leftR2 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205671",1)
    ));
    Map<String,Integer> rightR2 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205628",1)
    ));

    double rate2 = 1.0;

    Reaction r2 = new Reaction(name2,rate2,leftR2,rightR2);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    String name3 = "R-HSA-5205663";
    // [R-HSA-5205638,1] + [R-HSA-5205670,1]
    // -->
    // [R-HSA-5205648,1] , 1.0

    Map<String,Integer> leftR3 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205638",1),
            new Pair("R-HSA-5205670",1)
    ));
    Map<String,Integer> rightR3 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205648",1)
    ));

    double rate3 = 1.0;

    Reaction r3 = new Reaction(name3,rate3,leftR3,rightR3);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    String name4 = "R-HSA-5205652";
    // [R-HSA-201579,1] + [R-HSA-5205653,1]
    // -->
    // [R-HSA-5205683,1] , 1.0

    Map<String,Integer> leftR4 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-201579",1),
            new Pair("R-HSA-5205653",1)
    ));
    Map<String,Integer> rightR4 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205683",1)
    ));

    double rate4 = 1.0;

    Reaction r4 = new Reaction(name4,rate4,leftR4,rightR4);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    String name5 = "R-HSA-5205673";
    // [R-HSA-5205679,1] + [R-HSA-193937,1] + [R-HSA-5205624,1] + [R-HSA-5205630,1] + [R-HSA-5205668,1]
    // -->
    // [R-HSA-5205659,1] , 1.0

    Map<String,Integer> leftR5 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205679",1),
            new Pair("R-HSA-193937",1),
            new Pair("R-HSA-5205624",1),
            new Pair("R-HSA-5205630",1),
            new Pair("R-HSA-5205668",1)
    ));
    Map<String,Integer> rightR5 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205659",1)
    ));

    double rate5 = 1.0;

    Reaction r5 = new Reaction(name5,rate5,leftR5,rightR5);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    String name6 = "R-HSA-5205672";
    // [R-HSA-5205671,1]
    // -->
    // [R-HSA-5205653,1] , 1.0

    Map<String,Integer> leftR6 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205671",1)
    ));
    Map<String,Integer> rightR6 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205653",1)
    ));

    double rate6 = 1.0;

    Reaction r6 = new Reaction(name6,rate6,leftR6,rightR6);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    String name7 = "R-HSA-5205661";
    // [R-HSA-1252240,1] + [R-HSA-5205637,1]
    // -->
    // [R-HSA-5205671,1] + [R-HSA-1252240,1] , 1.0

    Map<String,Integer> leftR7 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-1252240",1),
            new Pair("R-HSA-5205637",1)
    ));
    Map<String,Integer> rightR7 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205671",1),
            new Pair("R-HSA-1252240",1)
    ));

    double rate7 = 1.0;

    Reaction r7 = new Reaction(name7,rate7,leftR7,rightR7);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    String name8 = "R-HSA-5205682";
    // [R-HSA-113595,12] + [R-HSA-1267988,1] + [R-HSA-5205683,1] + [R-HSA-5205688,1] + [R-HSA-992714,1] + [R-HSA-992745,1]
    // -->
    // [R-HSA-5205679,1] + [R-HSA-5205683,1] + [R-HSA-5205624,1] + [R-HSA-5205630,1] + [R-HSA-5205668,1] , 1.0

    Map<String,Integer> leftR8 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-113595",12),
            new Pair("R-HSA-1267988",1),
            new Pair("R-HSA-5205683",1),
            new Pair("R-HSA-5205688",1),
            new Pair("R-HSA-992714",1),
            new Pair("R-HSA-992745",1)
    ));
    Map<String,Integer> rightR8 = reactionComponentFactory(Set.of(
            new Pair("R-HSA-5205679",1),
            new Pair("R-HSA-5205683",1),
            new Pair("R-HSA-5205624",1),
            new Pair("R-HSA-5205630",1),
            new Pair("R-HSA-5205668",1)
    ));

    double rate8 = 1.0;

    Reaction r8 = new Reaction(name8,rate8,leftR8,rightR8);

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





    public Map<String,Integer> makeState(){

        Set<String> set3 = Set.of("R-HSA-113595","R-HSA-1267988","R-HSA-5205624",
                 "R-HSA-5205630","R-HSA-5205668","R-HSA-5205679",
                "R-HSA-5205688","R-HSA-992714","R-HSA-992745");
        Set<String> set = Set.of("R-HSA-113595","R-HSA-1267988","R-HSA-5205683",
                                "R-HSA-5205688","R-HSA-992714","R-HSA-992745",
                                "R-HSA-5205624","R-HSA-5205630","R-HSA-5205668","R-HSA-5205679");
        Set<String> set2 = Set.of("R-HSA-193937","R-HSA-5205624","R-HSA-5205630","R-HSA-5205668","R-HSA-5205679","R-HSA-113595","R-HSA-1267988","R-HSA-5205683","R-HSA-5205688","R-HSA-992714","R-HSA-992745");
        Set<String> set1 = Set.of("R-HSA-5205670","R-HSA-5205671","R-HSA-1252240","R-HSA-201579","R-HSA-5205679",
                "R-HSA-113595","R-HSA-5205659 ","R-HSA-992714","R-HSA-193937","R-HSA-5205671 ","R-HSA-5205637",
                "R-HSA-5205638","R-HSA-992745","R-HSA-5205630","R-HSA-5205668","R-HSA-1267988","R-HSA-5205624",
                "R-HSA-5205653","R-HSA-5205658","R-HSA-5205659","R-HSA-5205683","R-HSA-5205688");
        Map<String,Integer> initialState = new HashMap<>();
        for (String s : set){
            initialState.put(s,1000);
        }
        return initialState;
    }


    @Test
    public void testSim(){
        // List<Reaction> reactions = List.of(r1,r2,r3,r4,r5,r6,r7,r8);
        List<Reaction> reactions = List.of(r8);
        SimulationModel model = new SimulationModel(reactions,makeState());
        Simulation simulation = new Simulation(model);
        model.printReactions();
        simulation.printState();
        simulation.printPropensities();
        //simulation.simulateWithStepNumber(100);
        System.out.println();
        simulation.printState();
        simulation.printPropensities();
        for (int i=0;i<84;i++) {
            simulation.simulateWithStepNumber(1);
            System.out.println(i);
        }
//        int i = 83;
//        simulation.simulateWithStepNumber(1);
//        System.out.println(i);
        simulation.printPropensities();
        simulation.printState();
//        i = 84;
//        simulation.simulateWithStepNumber(1);
//        System.out.println(i);
    }

}
