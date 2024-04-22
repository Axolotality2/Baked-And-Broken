package BakeOrBreak.model;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DragIngredient extends Group {

    private final double bottomlineOffset = 9;
    private IngredientData ingredientData;

    public DragIngredient(BaseIngredient base) {
        this(base.getIngredientData());
        this.setId(ingredientData.getName());
        
        Bounds origPos = base.localToScene(base.getBoundsInLocal());
        
        this.showAt(origPos.getMinX(), origPos.getMinY());
    }

    public DragIngredient(IngredientData ingredientData) {
        this.ingredientData = ingredientData;
        this.setId(ingredientData.getName());
    }

    private boolean checkForIntersections() {
        boolean validLocation = false;
        Bounds itemBounds = this.localToScene(this.getBoundsInLocal());

        for (ItemReceiver zone : ItemReceiver.getItemZones()) {
            Bounds zoneBounds = zone.localToScene(zone.getBoundsInLocal());

            if (zoneBounds.contains(itemBounds.getMinX(), itemBounds.getMaxY() - bottomlineOffset)
                    && zoneBounds.contains(itemBounds.getMaxX(), itemBounds.getMaxY() - bottomlineOffset)) {
                zone.put(this);
                validLocation = true;
            }
        }

        return validLocation;
    }

    private void showAt(double x, double y) {
        ImageView iv = new ImageView(ingredientData.getImg());
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(iv);

        DragContext dragContext = new DragContext();

        this.addEventFilter(MouseEvent.ANY, (final MouseEvent mouseEvent) -> {
            mouseEvent.consume();
        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            this.layoutYProperty().unbind();

            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX
                    = iv.getTranslateX();
            dragContext.initialTranslateY
                    = iv.getTranslateY();
        });

        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, (final MouseEvent mouseEvent) -> {
            iv.setTranslateX(
                    dragContext.initialTranslateX
                    + mouseEvent.getX()
                    - dragContext.mouseAnchorX);
            iv.setTranslateY(
                    dragContext.initialTranslateY
                    + mouseEvent.getY()
                    - dragContext.mouseAnchorY);
        });

        this.addEventFilter(MouseEvent.MOUSE_RELEASED, (final MouseEvent mouseEvent) -> {
            if (!checkForIntersections()) {
                iv.setTranslateX(dragContext.initialTranslateX);
                iv.setTranslateY(dragContext.initialTranslateY);
                checkForIntersections();
            }
        });
    }

    private static class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    public IngredientData getIngredientData() {
        return ingredientData;
    }
}
