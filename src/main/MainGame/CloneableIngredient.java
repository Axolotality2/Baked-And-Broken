package main.MainGame;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.GsonFileReader;
import main.Core.FoodProcessing.Ingredient;

/**
 *
 * @author Rafael Inigo
 */
public class CloneableIngredient extends Ingredient {
    
    private static final ArrayList<CloneableIngredient> BASIC_INGREDIENTS = new ArrayList<>();
    private static Point2D placePoint = new Point2D(0, 0);
    private transient ImageView imageView;
    
    public CloneableIngredient(String name, boolean isFood, String[] allergens) {
        super(name, isFood, allergens);
        this.imageView = new ImageView(getImg());
        this.imageView.setId("BASE-" + name);
    }

    public CloneableIngredient(String name, boolean isFood, boolean allergenic) {
        super(name, isFood, allergenic);
        this.imageView = new ImageView(img);
        this.imageView.setId("BASE-" + name);
    }
    
    public CloneableIngredient(Ingredient ingredient) {
        super(ingredient.getName(), ingredient.isFood(), ingredient.getAllergens());
    }
    
    public void fillPantry(Pane pantryPane) {
        for (CloneableIngredient ci : BASIC_INGREDIENTS) {
            ci.imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
                new DraggableIngredient((Ingredient) this);
            });
            
            pantryPane.getChildren().add(imageView);
        }
    }
    
    public void setBASIC_INGREDIENTS(String location) {
        new GsonFileReader().fillArrList(BASIC_INGREDIENTS, location);
    }
    
    public static Point2D getPlacePoint() {
        return placePoint;
    }
    
    public static void setPlacePoint(Point2D point) {
        placePoint = point;
    }
}
