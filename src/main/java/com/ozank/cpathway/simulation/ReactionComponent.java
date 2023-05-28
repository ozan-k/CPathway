package com.ozank.cpathway.simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReactionComponent {
    private Map<Integer,Integer> reactionComponents= new HashMap<>();

    public  ReactionComponent(){}
    public ReactionComponent(Set<Pair<Integer>> list){
        for (Pair<Integer> p : list) {
            reactionComponents.put(p.getIndex(), p.getCount());
        }
    }

    public Map<Integer, Integer> getReactionComponents() {
        return reactionComponents;
    }

    public boolean containsKey(int key){
        return reactionComponents.containsKey(key);
    }

    public int get(int key){
        return reactionComponents.get(key);
    }

    public Set<Integer> getKeys(){
        return reactionComponents.keySet();
    }

    public String myToString(List<String> moleculesList) {
        String result = "";
        for (Integer moleculeIndex : reactionComponents.keySet()){
            result += "[" + moleculesList.get(moleculeIndex)
                    + "," + reactionComponents.get(moleculeIndex) + "] ";
        }
        return result;
    }
}
