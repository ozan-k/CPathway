package com.ozank.cpathway.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Matrix<T> {
    private Map<T,Integer> matrix = new HashMap<>();

    public Map<T, Integer> getMatrixM() {
        return matrix;
    }

    public int get(T p){
        return matrix.get(p);
    }

    public void set(T p,int value){

        matrix.put(p,value);
    }

    public boolean containsKey(T p){
        return matrix.containsKey(p);
    }
    public void increment(T p){
        int value = ( matrix.containsKey(p) ? matrix.get(p) : 0);
        matrix.put(p, value+1);
    }
    public void add(T p,int addedValue){
        int value = ( matrix.containsKey(p) ? matrix.get(p) : 0);
        matrix.put(p, value + addedValue);
    }

    public void decrement(T p){
        if (!matrix.containsKey(p)){
            throw new IllegalArgumentException();
        }
        int value = matrix.get(p);
        if (value==0) {
            throw new IllegalArgumentException();
        }
        matrix.put(p,value-1);
    }

    public Set<T> keySet(){
        return matrix.keySet();
    }
}
