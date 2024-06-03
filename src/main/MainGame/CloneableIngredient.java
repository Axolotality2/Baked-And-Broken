package main.MainGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Core.FoodProcessing.Ingredient;
import main.PlainFileReader;

/**
 *
 * @author Rafael Inigo
 */
public class CloneableIngredient extends ImageView {
    
    private static final ArrayList<Ingredient> BASIC_INGREDIENTS = new ArrayList<>();
    private Ingredient base;
    
    public CloneableIngredient(String name, boolean isFood, String[] allergens) {
        this.base = new Ingredient(name, isFood, allergens);
        this.setImage(base.getImg());
        this.setId("BASE-" + name);
    }

    public CloneableIngredient(String name, boolean isFood, boolean allergenic) {
        this.base = new Ingredient(name, isFood, allergenic);
        this.setImage(base.getImg());
        this.setId("BASE-" + name);
    }
    
    public CloneableIngredient(Ingredient ingredient) {
        this.base = new Ingredient(ingredient.getName(), ingredient.isFood(), ingredient.getAllergens());
        this.setImage(base.getImg());
        this.setId("BASE-" + base.getName());
    }
    
    public static void fillPantry(Pane pantryPane) { 
        pantryPane.getChildren().clear();
        
        for (Ingredient i : BASIC_INGREDIENTS) {
            CloneableIngredient ci = new CloneableIngredient(i);
            
            ci.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
                new DraggableIngredient(i).draw();
            }); 
            
            pantryPane.getChildren().add(ci);
        }
    }
    
    public static void setBASIC_INGREDIENTS(String location) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Type type = new TypeToken<IngredientPOJO[]>() {}.getType();
        IngredientPOJO[] contents = gson.fromJson(PlainFileReader.getFileContents(location), type);
        ArrayList<Ingredient> ingredientArrList = new ArrayList<>();
        
        for (IngredientPOJO ip : contents) {
            ingredientArrList.add(new Ingredient(ip.name, ip.isFood, ip.allergens));
        }
        
        BASIC_INGREDIENTS.addAll(ingredientArrList);
    }

    private class IngredientPOJO {

        private String name;
        private String[] allergens;
        private boolean isFood;
    }
}
