package main.MainGame;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Core.FoodProcessing.Ingredient;

public class DraggableIngredient extends Group {

    private static final ArrayList<Receptacle> receptacles = new ArrayList<>();
    private static Pane dragPane;
    private static Point2D dropPoint = new Point2D(0d, 0d);
    private final Ingredient ingredient;
    private final ImageView iv;
    private final DragContext dragContext;

    public DraggableIngredient(Ingredient ingredient) {
        super();
        this.ingredient = ingredient;
        iv = new ImageView(ingredient.getImg());
        dragContext = new DragContext();

        this.setId("DRAG-" + ingredient.getName() + "-" + System.currentTimeMillis());
    }

    public final void draw() {
        this.layoutYProperty().unbind();
        this.setLayoutX(dropPoint.getX());
        this.setLayoutY(dropPoint.getY());
        this.getChildren().add(iv);
        dragPane.getChildren().add(this);
        addHandlers();
    }

    public final void draw(Point2D position) {
        this.layoutYProperty().unbind();
        this.setLayoutX(position.getX());
        this.setLayoutY(position.getY());
        this.getChildren().add(iv);
        dragPane.getChildren().add(this);
        addHandlers();
    }

    private void addHandlers() {
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

    public boolean checkForIntersections() {
        boolean validLocation = false;

        Bounds thisBounds = this.localToScene(this.getBoundsInLocal());
        System.out.println(this);
        listCoords(thisBounds);
        System.out.println();
        
        for (Receptacle receptacle : receptacles) {
            Bounds receptacleBounds = receptacle.localToScene(receptacle.getBoundsInLocal());
            
            listCoords(receptacleBounds);
            System.out.print("-" + containsBottom(receptacleBounds, thisBounds) + "\n");
            
            if (containsBottom(receptacleBounds, thisBounds)) {
                receptacle.put(this);
                validLocation = true;
            }
        }
        
        System.out.println("\n\n\n\n");

        if (!validLocation) {
            returnToOriginalPos();
        }

        return validLocation;
    }

    public void returnToOriginalPos() {
        iv.setTranslateX(dragContext.initialTranslateX);
        iv.setTranslateY(dragContext.initialTranslateY);
    }

    private void listCoords(Bounds b) {
        System.out.print("(" + b.getMinX() + ", " + b.getMinY() + ") to (" + b.getMaxX() + ", " + b.getMaxY() + ")");
    }
    
    private boolean containsBottom(Bounds container, Bounds object) {
        return container.contains(new Point2D(object.getMinX(), object.getMaxY()))
                    && container.contains(new Point2D(object.getMaxX(), object.getMaxY()));
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

    public ImageView getIv() {
        return iv;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public static void setDragPane(Pane p) {
        dragPane = p;
    }

    public static void setDropPoint(Point2D aDropPoint) {
        dropPoint = aDropPoint;
    }

    /**
     * @return the receptacles
     */
    public static ArrayList<Receptacle> getReceptacles() {
        return receptacles;
    }
}
