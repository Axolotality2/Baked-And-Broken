package main.MainGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Rafael Inigo
 */
public abstract class Receptacle extends ImageView {
    
    public Receptacle(Image img) {
        super(img);
        DraggableIngredient.getReceptacles().add(this);
    }

    public abstract void put(DraggableIngredient ingredient);
}
