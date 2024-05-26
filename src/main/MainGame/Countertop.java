package main.MainGame;

/**
 *
 * @author Rafael Inigo
 */
public class Countertop extends Receptacle {

    public Countertop(String location) {
        super(location);
    }

    @Override
    public void put(DraggableIngredient ingredient) {
        double offset = ingredient.getIngredientNode().getLayoutY() - this.getBoundsInParent().getMinY();
        ingredient.getIngredientImg().layoutYProperty().bind(this.layoutYProperty().add(offset));
    }
    
}
