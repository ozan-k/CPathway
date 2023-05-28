package com.ozank.cpathway.dataclasses;

import com.ozank.cpathway.simulation.Reaction;

import java.util.HashMap;
import java.util.Set;

public class ReactionDataModel {
    private HashMap<String, HashMap<String, Reaction>> theReactionMap;
    private HashMap<String,HashMap<String,String>> participantNames;

    public ReactionDataModel(String species, Set<String> chosenPathways) {
        ReactionData reactionData = new ReactionData();
        SpeciesReactions theReactionMap = reactionData.getTheMap().get(species);
        this.theReactionMap = new HashMap<>();
        this.participantNames = new HashMap<>();
        for (String pathway : chosenPathways) {
            HashMap<String,Reaction> pathwayReactions = new HashMap<>();
            if (theReactionMap.getReactionsInPathways().containsKey(pathway)) {
                for (String reaction : theReactionMap.getReactionsInPathways().get(pathway)) {
                    pathwayReactions.put(reaction,theReactionMap.getReactionParticipants().get(reaction));
                    participantNames.put(reaction,theReactionMap.getParticipantNames().get(reaction));
                }
                this.theReactionMap.put(pathway,pathwayReactions);
            }
        }
    }

    public HashMap<String, HashMap<String, Reaction>> getTheReactionMap() {
        return theReactionMap;
    }

    public HashMap<String, HashMap<String, String>> getParticipantNames() {
        return participantNames;
    }
}

