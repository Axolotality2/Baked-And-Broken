package main.MainGame;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Rafael Inigo
 */
public class Countertop extends Receptacle {

    public SplitPane sp;

    public Countertop(Image location) {
        super(location);
    }

    @Override
    public void put(DraggableIngredient ingredient) {
        if (sp != null) {
            DoubleBinding height = sp.getDividers().get(0).positionProperty().multiply(sp.getHeight());
            System.out.println(height.get());
            ingredient.layoutYProperty().bind(height);
        }
    }

    public void setSP(SplitPane ap) {
        this.sp = ap;
    }

}
