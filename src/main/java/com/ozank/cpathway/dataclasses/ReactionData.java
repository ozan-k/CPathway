package com.ozank.cpathway.dataclasses;

import java.util.ArrayList;

public class ReactionData {
    private ArrayList<ArrayList<String>> input;
    private ArrayList<ArrayList<String>> output;
    private ArrayList<String> catalysts;
    private ArrayList<String> positiveReg;
    private ArrayList<String> negativeReg;


    public ReactionData(ArrayList<ArrayList<String>> input,
                        ArrayList<ArrayList<String>> output,
                        ArrayList<String> catalysts,
                        ArrayList<String> positiveReg,
                        ArrayList<String> negativeReg) {
        this.input = input;
        this.output = output;
        this.catalysts = catalysts;
        this.positiveReg = positiveReg;
        this.negativeReg = negativeReg;
    }

    public ArrayList<ArrayList<String>> getInput() {
        return input;
    }

    public ArrayList<ArrayList<String>> getOutput() {
        return output;
    }

    public ArrayList<String> getCatalysts() {
        return catalysts;
    }

    public ArrayList<String> getPositiveReg() {
        return positiveReg;
    }

    public ArrayList<String> getNegativeReg() {
        return negativeReg;
    }
}
