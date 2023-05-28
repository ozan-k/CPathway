package com.ozank.cpathway.simulation;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationModel {

    private int[] state;
    private double[] reactionRates;
    private ReactionComponent[] left;
    private ReactionComponent[] right;
    private DependentReactions[] reactionDependencies;
    private List<String> moleculesList;
    private Map<String,Integer>  speciesMap;

    public SimulationModel(List<Reaction> reactions,Set<Pair<String>> initialState){
        int size = reactions.size()+1;

        reactionRates = new double[size];
        left = new ReactionComponent[size];
        right = new ReactionComponent[size];

        moleculesList = getMoleculesList(reactions,initialState);
        speciesMap = getSpeciesMap(moleculesList);

        state = new int[speciesMap.size()];
        for (Pair p : initialState){
            state[speciesMap.get(p.getName())] = p.getCount();
        }

        int counter = 1;
        for (Reaction r : reactions){
            reactionRates[counter] = r.getRate();
            left[counter] = new ReactionComponent(r.getReactantsStoic(speciesMap));
            right[counter] = new ReactionComponent(r.getProductsStoic(speciesMap));
            counter++;
        }

        reactionDependencies = new DependentReactions[size];
        for (int i = 1; i < reactionRates.length; i++){
            Set<Integer> moleculesLeft = left[i].getKeys();
            Set<Integer> moleculesRight = right[i].getKeys();
            List<Set<Integer>> setList =
                    List.of(moleculesLeft, moleculesRight);
            Set<Integer> moleculesAll = setList.stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            reactionDependencies[i] = getReactionDependenciesOf(moleculesAll);
        }
    }

    private List<String> getMoleculesList(List<Reaction> reactions,
                                                     Set<Pair<String>> initialState) {
        Set<String> molecules = new TreeSet();
        for (Reaction r : reactions) {
            molecules.addAll(getReactionComponentSpecies(r.getReactants()));
            molecules.addAll(getReactionComponentSpecies(r.getProducts()));
        }
        for (Pair<String> p : initialState) {
            molecules.add(p.getName());
        }
        return molecules.stream().toList();
    }

    private  Map<String,Integer> getSpeciesMap(List<String> molecules){
        Map<String,Integer> speciesMap = new HashMap<>();
        for (int i=0; i < molecules.size() ; i++){
            speciesMap.put(molecules.get(i),i);
        }
        return speciesMap;
    }

    private static Set<String> getReactionComponentSpecies(Set<Pair<String>> set){
        return set.stream().map(pair -> pair.getName()).collect(Collectors.toSet());
    }


    private DependentReactions getReactionDependenciesOf(Set<Integer> molecules){
        DependentReactions result = new DependentReactions();
        for (int i =1; i<left.length; i++){
            if (left[i].getKeys()
                    .stream()
                    .filter(x -> molecules.contains(x))
                    .findAny()
                    .isPresent()){
                result.add(i);
            }
        }
        return result;
    };
    // -----------------------------------------------

    public int[] getState() {
        return state;
    }

    public double[] getReactionRates() {
        return reactionRates;
    }

    public ReactionComponent[] getLeft() {
        return left;
    }

    public ReactionComponent[] getRight() {
        return right;
    }

    public DependentReactions[] getReactionDependencies() {
        return reactionDependencies;
    }

    public List<String> getMoleculesList() {
        return moleculesList;
    }

    public Map<String, Integer> getSpeciesMap() {
        return speciesMap;
    }

    // -----------------------------------------------

    public void printState(){
        System.out.println("State");
        for (int i=0; i < state.length;i++){
            System.out.println(i + " : " + moleculesList.get(i) + " = " +state[i]);
        }
        System.out.println();
    }

    public void printReactions(){
        System.out.println("Model reactions");
        for (int i = 1; i<left.length;i++){
            System.out.println(i + " : "
                    + left[i].myToString(moleculesList)
                    + "--> "
                    + right[i].myToString(moleculesList)
                    + ", " + reactionRates[i] );
        }
        System.out.println();
    }

    public void printDependencies(){
        System.out.println("Reaction dependencies");
        for (int i = 1; i<reactionDependencies.length;i++){
            System.out.println(i + " : " + reactionDependencies[i].toString());
        }
        System.out.println();
    }

}
