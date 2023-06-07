package com.ozank.cpathway.fluxgraph;

import com.brunomnsilva.smartgraph.graphview.SmartLabelSource;

public class GEdge {  private int weight;
    private String stId;
    private String source;
    private String target;

    public GEdge(String source, String target, int weight,String stId) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.stId = stId;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    public String getStId() { return stId; }

    @SmartLabelSource
    public String getDisplayDistance() {
        /* If the above annotation is not present, the toString()
        will be used as the edge label. */

        return weight + " x " +  stId;
    }

    @Override
    public String toString() {
        return "Weight{" + "weight=" + weight + ",stId = " + stId + '}';
    }

}
