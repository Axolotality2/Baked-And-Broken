package BakeOrBreak.model;

import java.util.HashMap;
import java.util.Random;

public class WeightedDist<T> {

    private HashMap<T, Double> table;
    private double weightSum;
    private final Random generator = new Random();
    
    public boolean addEntry(T entry, double weight) {
        weightSum += weight;
        return table.putIfAbsent(entry, weightSum) == null;
    }
    
    public boolean editEntry(T entry, double weight) {
        if (table.containsKey(entry)) {
            weightSum -= table.get(entry) - weight;
            table.put(entry, weightSum);
        }
        return table.containsKey(entry);
    }
    
    public boolean deleteEntry(T entry) {
        if (table.containsKey(entry)) {
            weightSum -= table.get(entry);
            table.remove(entry);
        }
        return table.containsKey(entry);
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
}