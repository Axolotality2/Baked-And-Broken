package main.MainGame;

import main.MainGame.DraggableIngredient;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 *
 * @author Rafael Inigo
 */
public abstract class Receptacle extends ImageView {

    public Receptacle(String location) {
        super(location);
    }

    public abstract void put(DraggableIngredient ingredient);

    public boolean canHold(Node node) {
        Bounds thisBounds = this.localToScene(this.getBoundsInLocal());
        Bounds otherBounds = this.localToScene(node.getBoundsInLocal());

        return thisBounds.contains(new Point2D(otherBounds.getMinX(), otherBounds.getMaxY()))
                && thisBounds.contains(new Point2D(otherBounds.getMaxX(), otherBounds.getMaxY()));
    }

}
