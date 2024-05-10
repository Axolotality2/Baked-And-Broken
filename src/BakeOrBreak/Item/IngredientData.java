package BakeOrBreak.Item;

import javafx.scene.image.Image;

public class IngredientData {
    
    private final String name;
    private final String[] allergens;
    private final boolean isFood;
    private final transient Image img;
    
    public IngredientData(String name, boolean isFood, boolean allergenic) {
        this.name = name;        
        this.isFood = isFood;
        this.allergens = allergenic ? new String[0] : new String[] {name};
        this.img = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
    }
    
    public IngredientData(String name, boolean isFood, String[] allergens) {
        this.name = name;        
        this.isFood = isFood;
        this.allergens = allergens;
        this.img = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
    }
    
    public IngredientData(IngredientData ingredientData) {
        this(ingredientData.name, ingredientData.isFood, ingredientData.allergens);
    }

    public String getName() {
        return name;
    }

    public String[] getAllergens() {
        return allergens;
    }

    public boolean isFood() {
        return isFood;
    }

    public Image getImg() {
        return img;
    }
}
