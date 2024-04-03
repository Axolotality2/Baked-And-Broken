package BakeOrBreak.model;

import javafx.scene.image.Image;

public class Ingredient {
    private static final WeightedDist<Ingredient> ALLERGENS = new WeightedDist<>();
    
    private final Ingredient[] allergens;
    private final String name;
    private final Image image;
    private final Step[] steps;
    private final boolean isFood;
    
    // best for raw ingredients   
    public Ingredient (String name, boolean isFood, boolean isAllergen) {
        this.name = name;
        this.steps = new Step[0];
        this.isFood = isFood;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
        
        if (isAllergen) 
            this.allergens = new Ingredient[] { this };
        else 
            this.allergens = new Ingredient[0];
    }
    
    public Ingredient (String name, Step[] steps, boolean isFood, Ingredient[] allergens) {
        this.name = name;
        this.steps = steps;
        this.isFood = isFood;
        this.image = new Image(getClass().getResourceAsStream("/q3aa2_tau_regaladorm/view/assets/" + name + ".png"));
        this.allergens = allergens;
    }

    public static WeightedDist<Ingredient> getALLERGENS() {
        return ALLERGENS;
    }

    public Ingredient[] getAllergens() {
        return allergens;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Step[] getSteps() {
        return steps;
    }

    public boolean isIsFood() {
        return isFood;
    }
}
