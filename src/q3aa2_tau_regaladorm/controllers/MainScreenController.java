package q3aa2_tau_regaladorm.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import q3aa2_tau_regaladorm.model.*;

public class MainScreenController implements Initializable {

    @FXML
    private FlowPane pantry;

    @FXML
    private Pane dragPane;

    @FXML
    private ImageView customer;

    private ImageView activeItem;
    private GameMngr gameMngr = GameMngr.getGameManager();
    private boolean overCustomer = false;

    // customer enter
    private void customerEnter() throws IndexOutOfBoundsException {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), customer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setDelay(Duration.millis(500));

        fadeIn.play();
    }

    // customer exit
    private void customerExit() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), customer);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        fadeOut.setDelay(Duration.millis(500));

        fadeOut.play();
    }

    private class BaseIngredient extends ImageView {

        private Ingredient ingredient;

        private BaseIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;

            this.setImage(ingredient.getImage());
            this.setId("base-" + ingredient.getName());

            this.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
                DragIngredient dragIngredient = new DragIngredient(this);

                dragPane.getChildren().add(dragIngredient);
                dragIngredient.requestFocus();
            });

        }
    }

    private class DragIngredient extends Group {

        private Ingredient ingredient;

        private DragIngredient(BaseIngredient base) {
            final DragContext dragContext = new DragContext();
            this.ingredient = base.ingredient;

            ImageView iv = new ImageView(((ImageView) base).getImage());
            this.setId(base.getId().split("-")[1]);
            this.setLayoutX(base.localToScene(base.getBoundsInLocal()).getMinX());
            this.setLayoutY(base.localToScene(base.getBoundsInLocal()).getMinY());
            this.getChildren().add(iv);

            this.addEventFilter(MouseEvent.ANY, (final MouseEvent mouseEvent) -> {
                mouseEvent.consume();
            });

            this.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
                activeItem = (ImageView) iv;

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
        }
    }

    private static class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private abstract class ItemZone extends ImageView {
        abstract void lower(DragIngredient ingredient);
    }
    
    private class HoldZone extends ItemZone {

        @Override
        void lower(DragIngredient ingredient) {
            
        }
    }
    
    private abstract class DropZone extends ItemZone {
        private final ArrayList<DragIngredient> inventory = new ArrayList<>();
        
        protected ArrayList<DragIngredient> getInventory() {
            return inventory;
        }
    }
    
    private class CustomerNode extends DropZone {

        @Override
        void lower(DragIngredient ingredient) {
            this.getInventory().add(ingredient);
        }
        
    }
    
    private class WorkstationNode extends DropZone {

        @Override
        void lower(DragIngredient ingredient) {
            this.getInventory().add(ingredient);
        }
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dragPane.setPickOnBounds(false);

        ArrayList<BaseIngredient> bases = new ArrayList<>();
        Ingredient[] ingredients = new Ingredient[]{
            new Ingredient("flour", true, false),
            new Ingredient("milk", true, false),
            new Ingredient("sugar", true, false),
            new Ingredient("cheese", true, false),
            new Ingredient("butter", true, false),
            new Ingredient("yeast", true, false),
            new Ingredient("egg", true, false)
        };
        
        for (Ingredient ing : ingredients)
            bases.add(new BaseIngredient(ing));
        
        pantry.getChildren().addAll(bases);
        customerEnter();
    }

}
