package main.Core.CustomerGen;

import main.Core.FoodProcessing.Ingredient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user
 */
public class Difficulty {

    private final WeightDist<Integer> complexity;
    private final WeightDist<Integer> orderSize;
    private final WeightDist<Integer> allergyCount;
    private final WeightDist<String> allergies;

    public Difficulty(int difficulty) {
        complexity = new WeightDist<>();
        orderSize = new WeightDist<>();
        allergyCount = new WeightDist<>();
        allergies = new WeightDist<>();

        for (int i = 1; i <= difficulty * 10; i++) {
            complexity.addEntry(i, i);
        }

        for (int i = 1; i <= difficulty; i++) {
            orderSize.addEntry(i, difficulty - i);
        }

        for (int i = 0; i <= difficulty; i++) {
            allergyCount.addEntry(i, Math.pow(0.5, i));
        }
        
        for (String allergy : Ingredient.getVALID_ALLERGENS()) {
            allergies.addEntry(allergy, 1);
        }
    }

    public int orderSize() {
        return orderSize.pickRandom();
    }

    public int complexity() {
        return complexity.pickRandom();
    }

    public Set<String> allergies() {
        return new HashSet<>(Arrays.asList(allergies.pickRandom(allergyCount.pickRandom())));
    }
}
