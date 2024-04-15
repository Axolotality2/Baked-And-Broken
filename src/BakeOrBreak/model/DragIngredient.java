package BakeOrBreak.model;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DragIngredient extends Group {

    private final double bottomlineOffset = 9;
    protected String name;
    protected transient Image image;
    protected boolean isFood;
    protected BaseIngredient[] allergens;

    public DragIngredient(BaseIngredient base) {
        this(base.getName(), 
                base.isFood(), 
                base.isAllergen() ? new BaseIngredient[]{base} : new BaseIngredient[]{});
        this.setId(name);

        ImageView iv = this.addImageView(base.getImage(), 
                base.localToScene(base.getBoundsInLocal()).getMinX(), 
                base.localToScene(base.getBoundsInLocal()).getMinY());
        this.addMouseHandlers(iv);
    }

    public DragIngredient(String name, boolean isFood, BaseIngredient[] allergens) {
        this.name = name;
        this.isFood = isFood;
        this.allergens = allergens;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png"));
        this.setId(name);
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

    private ImageView addImageView(Image i, double x, double y) {
        ImageView iv = new ImageView(i);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(iv);
        
        return iv;
    }

    private void addMouseHandlers(ImageView iv) {
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

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public boolean isFood() {
        return isFood;
    }

    public BaseIngredient[] getAllergens() {
        return allergens;
    }
}
