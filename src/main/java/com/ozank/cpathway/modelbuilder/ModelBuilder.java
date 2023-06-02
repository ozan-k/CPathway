package com.ozank.cpathway.modelbuilder;

import com.ozank.cpathway.dataclasses.Molecule;
import com.ozank.cpathway.dataclasses.ReactionData;
import com.ozank.cpathway.simulation.*;

import java.util.*;

public class ModelBuilder {
    private final List<Reaction> reactions = new ArrayList<>();
    private final Set<String> upMolecules;
    private final Set<String> downMolecules;
    private final int counts;
    private final int upPerturbation;
    private final int downPerturbation;
    private final List<String> modelMolecules;
    public ModelBuilder(
            HashMap<String, HashMap<String, ReactionData>> reactionsInPathways,
            List<String> modelMolecules,
            Integer counts,
            Integer perturbation) {

        this.upPerturbation = counts + perturbation * counts / 100;
        this.downPerturbation = counts - perturbation * counts / 100;
        this.counts = counts;
        this.modelMolecules = modelMolecules;

        this.upMolecules = Molecule.getPerturbedUpMolecules();
        this.downMolecules = Molecule.getPerturbedDownMolecules();

        // Collecting reactions
        Reaction reaction;
        for (String pathwayId : reactionsInPathways.keySet()) {
            HashMap<String, ReactionData> pathwayReactions = reactionsInPathways.get(pathwayId);
            for (String reactionId : pathwayReactions.keySet()) {
                //System.out.println(reactionId);
                ReactionData r = pathwayReactions.get(reactionId);
                double reactionRate = 1.0F / (r.getPositiveReg().size() + r.getNegativeReg().size() + 1.0F);
                Map<String, Integer> reactants = getPreMap(r.getInput(), r.getCatalysts());
                Map<String, Integer> products = getPreMap(r.getOutput(), r.getCatalysts());
                reaction = buildReaction(reactionId, reactionRate, reactants, products, false, false, "");
                reactions.add(reaction);
                // ------------------------------------------------
                int k = 1;
                for (String regulator : r.getPositiveReg()) {
                    reaction = buildReaction(reactionId + "_p" + k, reactionRate, reactants, products, true, false, regulator);
                    reactions.add(reaction);
                    k++;
                }
                for (String regulator : r.getNegativeReg()) {
                    reaction = buildReaction(reactionId + "_n" + k, reactionRate, reactants, products, false, true, regulator);
                    reactions.add(reaction);
                    k++;
                }
            }
        }
    }

    private Map<String, Integer> getPreMap(ArrayList<ArrayList<String>> reactionDataList,
                                           List<String> catalystList) {
        Map<String, Integer> preMap = new HashMap<>();
        int count;
        String stdId;
        for (List<String> item : reactionDataList) {
            stdId = item.get(1);
            count = Integer.parseInt(item.get(0));
            if (preMap.containsKey(stdId)) {
                count += preMap.get(stdId);
            }
            preMap.put(stdId, count);
        }
        for (String item : catalystList) {
            count = (preMap.containsKey(item) ? preMap.get(item) + 1 : 1);
            preMap.put(item, count);
        }
        return preMap;
    }


    private Reaction buildReaction(String stId,
                                   double rate,
                                   Map<String, Integer> reactants,
                                   Map<String, Integer> products,
                                   boolean posReg,
                                   boolean negReg,
                                   String regulator) {
        if (posReg && !negReg) {
            Map<String, Integer> reactantsN = new HashMap<>(reactants);
            Map<String, Integer> productsN = new HashMap<>(products);
            reactantsN.put(regulator, 1);
            productsN.put(regulator, 1);
            return new Reaction(stId, rate, reactantsN, productsN);
        } else if (!posReg && negReg) {
            Map<String, Integer> reactantsN = new HashMap<>(reactants);
            Map<String, Integer> productsN = new HashMap<>();
            reactantsN.put(regulator, 1);
            productsN.put(regulator, 1);
            return new Reaction(stId, rate, reactantsN, productsN);
        } else if (!posReg) {
            return new Reaction(stId, rate, reactants, products);
        } else {
            return null;
        }
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Map<String, Integer> getControlInitialState(){
        Map<String, Integer> controlInitialState = new TreeMap<>();
        for (String id : modelMolecules){
            controlInitialState.put(id,counts);
        }
        return controlInitialState;
    }

    private int getMoleculeCount(String molecule){
            if (upMolecules.contains(molecule)){
                return upPerturbation;
            } else if (downMolecules.contains(molecule)){
                return downPerturbation;
            } else {
                return counts;
            }
    }

    public Map<String, Integer> getCaseInitialState() {
        Map<String, Integer> initialState = new TreeMap<>();
        for (String id : modelMolecules){
            initialState.put(id,getMoleculeCount(id));
        }
        return initialState;
    }

}
