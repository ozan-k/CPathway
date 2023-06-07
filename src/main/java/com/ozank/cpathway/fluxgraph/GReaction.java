package com.ozank.cpathway.fluxgraph;

import com.brunomnsilva.smartgraph.graphview.SmartLabelSource;

public class GReaction {
    private String stId;
    private String name;

    public GReaction(String stId, String name) {
        this.stId = stId;
        this.name = name;
    }

    @SmartLabelSource
    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Reaction{" + "stId=" + stId + ", name=" + name + '}';
    }


}
