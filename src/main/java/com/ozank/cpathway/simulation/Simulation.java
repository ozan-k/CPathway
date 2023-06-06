package com.ozank.cpathway.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation {
    Random r = new Random();
    private final SimulationModel model;
    private final Matrix<PairIndex> matrixM;
    private final Matrix<TripleIndex> matrixF;
    private final double[] aj;
    private final int[] state;
    private final int[] state_y;

    private final String[] reactionIds;
    private final ReactionComponent[] reactionsLeft;
    private final ReactionComponent[] reactionsRight;
    private final double[] reactionRates;
    private final DependentReactions[] reactionDependencies;

    private final List<String> moleculesList;

    public Simulation(SimulationModel model){
        this.model = model;

        int[] initialState = model.getState();
        state = Arrays.copyOf(initialState,initialState.length);
        state_y = Arrays.copyOf(initialState,initialState.length);

        reactionsLeft = model.getLeft();
        reactionsRight = model.getRight();
        reactionRates = model.getReactionRates();
        reactionDependencies = model.getReactionDependencies();
        reactionIds = model.getReactionIds();
        moleculesList = model.getMoleculesList();

        aj = new double[reactionsLeft.length];
        for (int i = 1; i<reactionsLeft.length;i++) {
            aj[i] = computePropensity(i);
            aj[0] += aj[i];
        }

        matrixM = new Matrix<>();
        matrixF = new Matrix<>();
        for (int i=0;i<state.length;i++){
            matrixM.set(new PairIndex(i,0),state[i]);
        }
    }

    private double computePropensity(int i){
        int moleculeCount;
        double max = 0;
        ReactionComponent left = reactionsLeft[i];
        for (Integer moleculeIndex : left.keySet()){
            moleculeCount = state[moleculeIndex];
            if (moleculeCount==0 || left.get(moleculeIndex) > moleculeCount) { return 0; }
            if (moleculeCount > max) { max = moleculeCount; }
        }
        return max * reactionRates[i];
    }

    private int computeNextReaction(){
        double random = r.nextDouble() * aj[0];
        double sum =0;
        for (int i=1;i< aj.length;i++){
            sum+=aj[i];
            if (sum > random){
                return i;
            }
        }
        return 0;
    }


    private void updatePropensities(DependentReactions reactions){
        reactions.getDependentReactions()
                .stream()
                .forEach(i-> {
                    aj[0] -= aj[i];
                    aj[i] = computePropensity(i);
                    aj[0] += aj[i];
                });
    }

    private void updateState(int reactionIndex){
        ReactionComponent left = reactionsLeft[reactionIndex];
        ReactionComponent right = reactionsRight[reactionIndex];
        for (int i : left.keySet()){ state[i] -= left.get(i); }
        for (int i : right.keySet()){state[i] += right.get(i);}
    }


    private int getReactionOrigin(int speciesIndex){
        int random = r.nextInt(state_y[speciesIndex]);
        random++;
        int sum =0;
        for (int i=0;i<reactionsLeft.length;i++){
            PairIndex p = new PairIndex(speciesIndex,i);
            if (matrixM.containsKey(p)){
                sum+= matrixM.get(p);
                if (sum>=random){
                    return i;
                }
            }
        }
        return -1;
    }

    private void updateFluxes(int reactionIndex){
        ReactionComponent left = reactionsLeft[reactionIndex];
        for (Integer speciesIndex : left.keySet()){
            for (int i=0;i< left.get(speciesIndex);i++){
                int sourceReaction = getReactionOrigin(speciesIndex);
                matrixM.decrement(new PairIndex(speciesIndex,sourceReaction));
                state_y[speciesIndex]--;
                matrixF.increment(new TripleIndex(sourceReaction,reactionIndex,speciesIndex));
            }
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ReactionComponent right = reactionsRight[reactionIndex];
        for (Integer speciesIndex : right.keySet()){
            matrixM.add(new PairIndex(speciesIndex,reactionIndex),right.get(speciesIndex));
            state_y[speciesIndex] += right.get(speciesIndex);
        }
    }

    private void simulationStep(){
        int mu = computeNextReaction();
        updateState(mu);
        updatePropensities(reactionDependencies[mu]);
        //updateTrajectory();
        updateFluxes(mu);
    }

    public void simulateWithStepNumber(int n){
        for (int i=0; i<n; i++){
            if (aj[0] == 0) {
                break;
            } else {
                simulationStep();
            }
        }
    }


    public Matrix<PairIndex> getM(){
        return matrixM;
    }

    public Matrix<TripleIndex> getF(){
        return matrixF;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void printPropensities(){
        System.out.println("Reaction propensities");
        for (int i=0; i< aj.length;i++){
            System.out.println(i + " : " + aj[i]);
        }
        System.out.println();
    }

    public void printFluxes(){
        for (TripleIndex t : matrixF.keySet()){
            System.out.println(t.toString(model.getMoleculesList()) + " : " + matrixF.get(t));
        }
    }

    public String reactionIdOf(Integer i){
        return reactionIds[i];
    }

    public String[] getReactionIds(){
        return reactionIds;
    }

    public void printState(){
        for (int i=0;i<state.length;i++){
            if (state[i] >0){
                System.out.println(moleculesList.get(i) + " : " + state[i]);
            }
        }
    }

}


