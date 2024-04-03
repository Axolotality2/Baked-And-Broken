package BakeOrBreak.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import BakeOrBreak.model.*;

public class MainScreenController implements Initializable {

    @FXML
    private Pane dragPane;
    @FXML
    private FlowPane pantry;
    @FXML
    private AnchorPane customerBox;
    @FXML
    private AnchorPane counterPane;
    @FXML
    private ImageView clock;

    private WorkstationNode station;
    private ImageView activeItem;
    private GameMngr gameMngr = GameMngr.getGameManager();

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

            this.addEventFilter(MouseEvent.MOUSE_RELEASED, (final MouseEvent mouseEvent) -> {
                for (ItemZone zone : ItemZone.getItemZones()) {
                    Bounds itemBounds = this.localToScene(this.getBoundsInLocal());
                    Bounds zoneBounds = zone.localToScene(zone.getBoundsInLocal());
                    
                    System.out.println("this" + itemBounds);
                    System.out.println("zone" + zoneBounds);
                    
                    if (itemBounds.intersects(zoneBounds)) {
                        System.out.println("found");
                        zone.put(this);
                    }
                }
            });
        }

    }

    private static class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private static abstract class ItemZone extends ImageView {

        protected static final ArrayList<ItemZone> itemZones = new ArrayList<>();

        abstract void put(DragIngredient ingredient);

        private static ArrayList<ItemZone> getItemZones() {
            return itemZones;
        }
    }

    private class Countertop extends ItemZone {

        private Countertop() {
            itemZones.add(this);
        }
        
        @Override
        void put(DragIngredient ingredient) {
            System.out.println("hold");
            Pane parentPane = ((Pane)this.getParent());
            Bounds targetBounds = parentPane.sceneToLocal(ingredient.localToScene(ingredient.getBoundsInLocal()));
            
            parentPane.getChildren().add(ingredient);
            ingredient.setLayoutX(targetBounds.getMinX());
            ingredient.setLayoutY(targetBounds.getMinY());
        }
    }

    private abstract class DropZone extends ItemZone {

        private final ArrayList<DragIngredient> inventory = new ArrayList<>();

        protected ArrayList<DragIngredient> getInventory() {
            return inventory;
        }
    }

    private class CustomerNode extends DropZone {

        Customer customer;

        private CustomerNode(Customer customer) {
            this.customer = customer;

            this.setImage(new Image(getClass().getResourceAsStream("/q3aa2_tau_regaladorm/view/assets/customer.png")));
            customerBox.getChildren().add(this);

            this.setLayoutX(-(this.getBoundsInParent().getWidth() + 3));
            this.setLayoutY(69);
            
            itemZones.add(this);
        }

        // enter
        private void enter() {
            PathTransition pathTransition = new PathTransition();
            Path path = new Path();
            path.getElements().add(new MoveTo(0, 48));
            path.getElements().add(new LineTo(120, 48));

            pathTransition.setDuration(Duration.millis(1000));
            pathTransition.setNode(this);
            pathTransition.setPath(path);
            
            pathTransition.play();
        }

        // exit
        @Override
        void put(DragIngredient ingredient) {
            try {
                customer.rateProduct((Product) ingredient.ingredient);
                ((Pane)ingredient.getParent()).getChildren().remove(ingredient);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private class WorkstationNode extends DropZone {

        @Override
        void put(DragIngredient ingredient) {
            this.getInventory().add(ingredient);
            ((Pane)ingredient.getParent()).getChildren().remove(ingredient);
        }

    }

    private void initCounters() {
        // Setup kitchen counter
        Countertop kitchenCounter = new Countertop();
        kitchenCounter.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/kitchenTable.png")));
        kitchenCounter.setId("counter");
        
        AnchorPane.setBottomAnchor(kitchenCounter, 0d);
        counterPane.getChildren().add(kitchenCounter);
        
        // Setup cashier counter
        
    }
    
    private void initWorkstation() {
        WorkstationNode station = new WorkstationNode();
        station.setImage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dragPane.setPickOnBounds(false);
        initCounters();

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

        for (Ingredient ing : ingredients) {
            bases.add(new BaseIngredient(ing));
        }

        pantry.getChildren().addAll(bases);

//        Customer c = new Customer(new ArrayList<>(), new Ingredient[2]);
//
//        CustomerNode cn = new CustomerNode(c);
//
//        clock.setOnMouseClicked((final MouseEvent e) -> {
//            cn.enter();
//        });
    }

}
