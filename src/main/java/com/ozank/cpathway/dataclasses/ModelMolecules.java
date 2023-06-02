package com.ozank.cpathway.dataclasses;

import java.util.*;

public class ModelMolecules {

    private Set<String> modelMoleculesSet = new TreeSet<>();
    private List<String> modelMolecules;

    public ModelMolecules(HashMap<String, HashMap<String, ReactionData>> modelReactionsInPathways){
        for (String pathwayId : modelReactionsInPathways.keySet()) {
            HashMap<String, ReactionData> pathwayReactions = modelReactionsInPathways.get(pathwayId);
            for (String reactionId : pathwayReactions.keySet()) {
                ReactionData r = pathwayReactions.get(reactionId);
                addMolecules(r.getInput());
                addMolecules(r.getOutput());
                addMolecules(r.getNegativeReg());
                addMolecules(r.getPositiveReg());
                addMolecules(r.getCatalysts());
            }
        }
        modelMolecules = new ArrayList<>(modelMoleculesSet);
        modelMoleculesSet = null;
    }

    // Collecting reaction molecules.

    private void addMolecules(ArrayList<ArrayList<String>> inputAndOutput){
        for (List<String> item : inputAndOutput){
            modelMoleculesSet.add(item.get(1));
        }
    }
    private void addMolecules(List<String> set){
        for (String s : set){
            modelMoleculesSet.add(s);
        }
    }

    public List<String> getModelMolecules() {
        return modelMolecules;
    }

    public static HashMap<String,String> getMoleculeNames(HashMap<String, HashMap<String, String>> participantNames){
        HashMap<String,String> result = new HashMap<>();
        for (String reaction : participantNames.keySet()){
            HashMap<String,String> reactionMolecules = participantNames.get(reaction);
            for (String molecule : reactionMolecules.keySet()){
                result.put(molecule,reactionMolecules.get(molecule));
            }
        }
        return result;
    }
}
