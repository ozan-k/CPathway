package com.ozank.cpathway.dataclasses;

import com.ozank.cpathway.simulation.Reaction;

import java.util.ArrayList;
import java.util.HashMap;

public class SpeciesReactions {
    private HashMap<String,Reaction> reactionParticipants;
    private HashMap<String, ArrayList<String>> reactionsInPathways;
    private HashMap<String,HashMap<String,String>> participantNames;

    public SpeciesReactions(HashMap<String, Reaction> reactionParticipants,
                            HashMap<String, ArrayList<String>> reactionsInPathways,
                            HashMap<String, HashMap<String, String>> participantNames) {
        this.reactionParticipants = reactionParticipants;
        this.reactionsInPathways = reactionsInPathways;
        this.participantNames = participantNames;
    }

    public HashMap<String, Reaction> getReactionParticipants() {
        return reactionParticipants;
    }

    public HashMap<String, ArrayList<String>> getReactionsInPathways() {
        return reactionsInPathways;
    }

    public HashMap<String, HashMap<String, String>> getParticipantNames() {
        return participantNames;
    }

}
