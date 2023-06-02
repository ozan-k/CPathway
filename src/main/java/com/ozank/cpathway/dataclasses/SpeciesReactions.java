package com.ozank.cpathway.dataclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class SpeciesReactions {
    private HashMap<String, ReactionData> reactionParticipants;
    private HashMap<String, ArrayList<String>> reactionsInPathways;
    private HashMap<String,HashMap<String,String>> participantNames;

    public SpeciesReactions(HashMap<String, ReactionData> reactionParticipants,
                            HashMap<String, ArrayList<String>> reactionsInPathways,
                            HashMap<String, HashMap<String, String>> participantNames) {
        this.reactionParticipants = reactionParticipants;
        this.reactionsInPathways = reactionsInPathways;
        this.participantNames = participantNames;
    }

    public HashMap<String, ReactionData> getReactionParticipants() {
        return reactionParticipants;
    }

    public HashMap<String, ArrayList<String>> getReactionsInPathways() {
        return reactionsInPathways;
    }

    public HashMap<String, HashMap<String, String>> getParticipantNames() {
        return participantNames;
    }

}
