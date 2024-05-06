package BakeOrBreak.model;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DragIngredient extends Group {

    private static Pane dragPane;
    private final double bottomlineOffset = 9;
    private IngredientData ingredientData;
    private DragContext dragContext;

    public DragIngredient(BaseIngredient base) {
        this(base.getIngredientData());
        this.setId(ingredientData.getName());
        dragContext = new DragContext();

        Bounds origPos = base.localToScene(base.getBoundsInLocal());

        this.showAt(origPos.getMinX(), origPos.getMinY());
    }

    public DragIngredient(IngredientData ingredientData) {
        this.ingredientData = ingredientData;
        this.setId(ingredientData.getName());
        dragContext = new DragContext();
    }

    public boolean checkForIntersections() {
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

    public final void showAt(double x, double y) {
        ImageView iv = new ImageView(ingredientData.getImg());

        this.layoutYProperty().unbind();
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(iv);
        dragPane.getChildren().add(this);

        this.addEventFilter(MouseEvent.ANY, (final MouseEvent mouseEvent) -> {
            mouseEvent.consume();
        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            this.layoutYProperty().unbind();

            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX = iv.getTranslateX();
            dragContext.initialTranslateY = iv.getTranslateY();
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
                returnToOriginalPos();
                checkForIntersections();
            }
        });
    }

    public void returnToOriginalPos() {
        ImageView iv = (ImageView) this.getChildren().get(0);
        iv.setTranslateX(dragContext.initialTranslateX);
        iv.setTranslateY(dragContext.initialTranslateY);
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
    
    public static void setDragPane(Pane p) {
        dragPane = p;
    }
}
