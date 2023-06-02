package com.ozank.cpathway.dataclasses;

import java.util.HashMap;
import java.util.Set;

public class ReactionDataModel {
    private HashMap<String, HashMap<String, ReactionData>> reactionData;
    private HashMap<String,HashMap<String,String>> participantNames;

    public ReactionDataModel(String species, Set<String> chosenPathways) {
        AllReactionData allReactionData = new AllReactionData();
        SpeciesReactions allReactionsOfChosenSpecies = allReactionData.getAllReactionData().get(species);
        this.reactionData = new HashMap<>();
        this.participantNames = new HashMap<>();
        for (String pathway : chosenPathways) {
            HashMap<String,ReactionData> pathwayReactions = new HashMap<>();
            if (allReactionsOfChosenSpecies.getReactionsInPathways().containsKey(pathway)) {
                for (String reaction : allReactionsOfChosenSpecies.getReactionsInPathways().get(pathway)) {
                    pathwayReactions.put(reaction,allReactionsOfChosenSpecies.getReactionParticipants().get(reaction));
                    participantNames.put(reaction,allReactionsOfChosenSpecies.getParticipantNames().get(reaction));
                }
                this.reactionData.put(pathway,pathwayReactions);
            }
        }
    }

    public HashMap<String, HashMap<String, ReactionData>> getReactionData() {
        return reactionData;
    }

    public HashMap<String, HashMap<String, String>> getParticipantNames() {
        return participantNames;
    }
}

