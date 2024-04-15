package BakeOrBreak.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONIngredient {

    private final String name;
    private final boolean isFood;
    private final boolean isAllergen;

    public JSONIngredient(String name, boolean isFood, boolean isAllergen) {
        this.name = name;
        this.isFood = isFood;
        this.isAllergen = isAllergen;
    }

    public BaseIngredient toBaseIngredient() {
        return new BaseIngredient(name, isFood, isAllergen);
    }
}
