package com.ozank.cpathway.dataclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Species {
    private ArrayList<Pathway> pathways;
    private HashMap<String,ArrayList<String>> hierarchy;
    private HashMap<String,String> names;

    private ArrayList<Pathway> topLevelPathways;

    public Species(ArrayList<Pathway> pathways,
                   HashMap<String, ArrayList<String>> hierarchy,
                   HashMap<String, String> names,
                   ArrayList<Pathway> topLevelPathways) {
        this.pathways = pathways;
        this.hierarchy = hierarchy;
        this.names = names;
        this.topLevelPathways = topLevelPathways;
    }

    public ArrayList<Pathway> getPathways() {
        return pathways;
    }

    public HashMap<String, ArrayList<String>> getHierarchy() {
        return hierarchy;
    }

    public HashMap<String, String> getNames() {
        return names;
    }

    public ArrayList<Pathway> getTopLevelPathways() {
        return topLevelPathways;
    }
}

