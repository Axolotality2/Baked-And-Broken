package main.MainGame;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Core.FoodProcessing.Ingredient;

public class DraggableIngredient extends Ingredient {

    private static final ArrayList<Receptacle> receptacles = new ArrayList<>();
    private static Pane dragPane;
    private static Point2D dropPoint = new Point2D(0d, 0d);
    private final Group ingredientNode;
    private final ImageView ingredientImg;
    private final DragContext dragContext;

    public DraggableIngredient(Ingredient ingredient) {
        super(ingredient);
        ingredientNode = new Group();
        ingredientImg = new ImageView(img);
        dragContext = new DragContext();
        
        ingredientNode.setId("DRAG-" + name + "-" + System.currentTimeMillis());
    }

    public final void draw() {
        ingredientNode.layoutYProperty().unbind();
        ingredientNode.setLayoutX(dropPoint.getX());
        ingredientNode.setLayoutY(dropPoint.getY());
        ingredientNode.getChildren().add(ingredientImg);
        dragPane.getChildren().add(ingredientNode);

        ingredientNode.addEventFilter(MouseEvent.ANY, (final MouseEvent mouseEvent) -> {
            mouseEvent.consume();
        });

        ingredientNode.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            ingredientNode.layoutYProperty().unbind();

            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX = ingredientImg.getTranslateX();
            dragContext.initialTranslateY = ingredientImg.getTranslateY();
        });

        ingredientNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, (final MouseEvent mouseEvent) -> {
            ingredientImg.setTranslateX(
                    dragContext.initialTranslateX
                    + mouseEvent.getX()
                    - dragContext.mouseAnchorX);
            ingredientImg.setTranslateY(
                    dragContext.initialTranslateY
                    + mouseEvent.getY()
                    - dragContext.mouseAnchorY);
        });

        ingredientNode.addEventFilter(MouseEvent.MOUSE_RELEASED, (final MouseEvent mouseEvent) -> {
            if (!checkForIntersections()) {
                returnToOriginalPos();
                checkForIntersections();
            }
        });
    }

    public boolean checkForIntersections() {
        boolean validLocation = false;

        for (Receptacle receptacle : receptacles) {
            if (receptacle.canHold(ingredientImg)) {
                receptacle.put(this);
                dragPane.getChildren().remove(getIngredientNode());
                validLocation = true;
            }
        }

        if (!validLocation) {
            returnToOriginalPos();
        }
        
        return validLocation;
    }

    public void returnToOriginalPos() {
        ingredientImg.setTranslateX(dragContext.initialTranslateX);
        ingredientImg.setTranslateY(dragContext.initialTranslateY);
    }

    private static class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    public static Pane getDragPane() {
        return dragPane;
    }

    public static Point2D getDropPoint() {
        return dropPoint;
    }

    public Group getIngredientNode() {
        return ingredientNode;
    }

    public ImageView getIngredientImg() {
        return ingredientImg;
    }

    public static void setDragPane(Pane p) {
        dragPane = p;
    }
    
    public static void setDropPoint(Point2D aDropPoint) {
        dropPoint = aDropPoint;
    }
}
