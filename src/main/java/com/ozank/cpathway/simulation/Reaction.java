package com.ozank.cpathway.simulation;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Reaction {
    private double rate;
    private Set<Pair<String>> reactants;
    private Set<Pair<String>> products;

    public Reaction(double rate, Set<Pair<String>> reactants, Set<Pair<String>> products) {
        this.rate = rate;
        this.reactants = reactants;
        this.products = products;
    }

    public double getRate() {
        return rate;
    }

    public Set<Pair<String>> getReactants() {
        return reactants;
    }

    public Set<Pair<String>> getProducts() {
        return products;
    }

    public Set<Pair<Integer>> getReactantsStoic(Map<String,Integer> map) {
        return reactants
                .stream()
                .map(pair -> new Pair<>(map.get(pair.getName()), pair.getCount()))
                .collect(Collectors.toSet());
    }

    public Set<Pair<Integer>> getProductsStoic(Map<String,Integer> map) {
        return products
                .stream()
                .map(pair -> new Pair<>(map.get(pair.getName()), pair.getCount()))
                .collect(Collectors.toSet());
    }
}
