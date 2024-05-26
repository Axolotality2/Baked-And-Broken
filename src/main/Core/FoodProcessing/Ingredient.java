package main.Core.FoodProcessing;

import java.util.ArrayList;
import javafx.scene.image.Image;
import main.Core.CustomerGen.Product;
import main.GsonFileReader;

/**
 *
 * @author user
 */
public class Ingredient {
    
    private static final ArrayList<Ingredient> VALID_ALLERGENS = new ArrayList<>();
    
    protected final String name;
    protected final String[] allergens;
    protected final boolean isFood;
    protected final transient Image img;
    
    public Ingredient(String name, boolean isFood, boolean allergenic) {
        this.name = name;        
        this.isFood = isFood;
        this.allergens = allergenic ? new String[0] : new String[] {name};
        this.img = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
    }
    
    public Ingredient(String name, boolean isFood, String[] allergens) {
        this.name = name;        
        this.isFood = isFood;
        this.allergens = allergens;
        this.img = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
    }
    
    public Ingredient(Ingredient ingredient) {
        this(ingredient.name, ingredient.isFood, ingredient.allergens);
    }
    
    public Product toProduct() {
        for (Product product : Product.getValidProducts()) {
            if (this.name.equals(product.getName())) {
                return product;
            }
        }
        
        return null;
    }

    public static void setVALID_ALLERGENS(String location) {
        new GsonFileReader().fillArrList(VALID_ALLERGENS, location);
    }
    
    public static ArrayList<Ingredient> getVALID_ALLERGENS() {
        return VALID_ALLERGENS;
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