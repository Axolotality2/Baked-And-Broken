package BakeOrBreak.model;

import javafx.beans.property.DoubleProperty;

public class Countertop extends ItemReceiver {

    private final double initCounterHeight;
    private DoubleProperty finHeight;

    public Countertop() {
        itemZones.add(this);
        initCounterHeight = this.getBoundsInLocal().getMaxY();
    }

    @Override
    public void put(DragIngredient ingredient) {
        // MAJOR thanks to Christian Brandon Garcia of Charm '26 for this two-line solution
        // I never would've figured it out without him
        double offset = ingredient.getLayoutY() - this.getBoundsInParent().getMinY();
        ingredient.layoutYProperty().bind(this.layoutYProperty().add(offset));
    }

}
