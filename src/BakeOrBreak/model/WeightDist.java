package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WeightDist<T> {

    private final HashMap<T, Double> table = new HashMap<>();
    private double weightSum;
    private final Random generator = new Random();
    
    public WeightDist() {
        weightSum = 0d;
    }
    
    public boolean addEntry(T entry, double weight) {
        weightSum += weight;
        return table.put(entry, weightSum) == null;
    }
    
    public T pickRandom() {
        double reference = generator.nextDouble() * weightSum;

        for (T entry : table.keySet()) {
            if (table.get(entry) >= reference) {
                return entry;
            }
        }
        return null;
    }
    
    public T[] pickRandom(int length) {
        Object[] randomArr = new Object[length];
        
        for (int i = 0; i < length; i++)
            randomArr[i] = pickRandom();
        
        return (T[]) randomArr;
    }
    
    public ArrayList<T> getValues() {
        return new ArrayList<>(table.keySet());
    }
}