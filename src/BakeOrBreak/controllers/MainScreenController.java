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
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

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
    @FXML
    private Label objective;

    private final String ingredientsFilepath = "/BakeOrBreak/constants/ingredients.txt";
    private Countertop kitchenCounter;
    private Countertop cashierCounter;
    private WorkstationNode activeWorkstation;
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

        private Ingredient getIngredient() {
            return ingredient;
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

        private boolean checkForIntersections() {
            boolean validLocation = false;
            Bounds itemBounds = this.localToScene(this.getBoundsInLocal());
            
            for (ItemZone zone : ItemZone.getItemZones()) {
                Bounds zoneBounds = zone.localToScene(zone.getBoundsInLocal());

                if (zoneBounds.contains(itemBounds.getMinX(), itemBounds.getMaxY())
                        && zoneBounds.contains(itemBounds.getMaxX(), itemBounds.getMaxY())) {
                    zone.put(this);
                    validLocation = true;
                }
            }

            return validLocation;
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

        private final double initCounterHeight;
        private DoubleProperty finHeight;

        private Countertop() {
            itemZones.add(this);
            initCounterHeight = this.getBoundsInLocal().getMaxY();
        }

        @Override
        void put(DragIngredient ingredient) {
            // MAJOR thanks to Christian Brandon Garcia of Charm '26 for this two-line solution
            // I never would've figured it out without him
            double offset = ingredient.getLayoutY() - this.getBoundsInParent().getMinY();
            ingredient.layoutYProperty().bind(this.layoutYProperty().add(offset));
        }
    }

    private class CustomerNode extends ItemZone {

        Customer customer;

        private CustomerNode(Customer customer) {
            this.customer = customer;

            this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/customer.png")));
            customerBox.getChildren().add(this);

            this.setLayoutX(-(this.getBoundsInParent().getWidth() + 3));
            this.setLayoutY(69);

            itemZones.add(this);
        }

        // enter
        private void enter() {
            objective.setText("make a " + customer.getOrder().get(0).getName());

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
                ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private class WorkstationNode extends ItemZone {

        private Workstation workstation;

        private WorkstationNode(Workstation workstation) {
            this.setId("station-" + workstation.getName());
            this.setImage(workstation.getImage());
            this.workstation = workstation;

            itemZones.add(this);

            this.addEventHandler(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
                try {
                    workstation.use();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        @Override
        void put(DragIngredient ingredient) {
            workstation.insert(ingredient.ingredient);
            ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
        }
    }

    private void setWorkstation(String name) {
        Workstation workstation = new Workstation(name);
        WorkstationNode bowlView = new WorkstationNode(workstation);

        double centerPlacement = 3 * Math.round((kitchenCounter.getBoundsInParent().getWidth() - workstation.getImage().getWidth()) / 6);
        double heightPlacement = kitchenCounter.getBoundsInParent().getHeight() - 9;

        AnchorPane.setLeftAnchor(bowlView, centerPlacement);
        AnchorPane.setBottomAnchor(bowlView, heightPlacement);
        counterPane.getChildren().add(bowlView);
    }

    private void setWorkstation(int index) {
        setWorkstation(Workstation.getWorkstations().get(index).getName());
    }

    private void initCounters() {
        // Setup kitchen counter
        kitchenCounter = new Countertop();
        kitchenCounter.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/kitchenTable.png")));
        kitchenCounter.setId("kitchenCounter");

        AnchorPane.setBottomAnchor(kitchenCounter, 0d);
        counterPane.getChildren().add(kitchenCounter);

        // Setup cashier counter
        cashierCounter = new Countertop();
        cashierCounter.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/cashierTable.png")));
        cashierCounter.setId("cashierCounter");

        AnchorPane.setBottomAnchor(cashierCounter, 3d);
        AnchorPane.setLeftAnchor(cashierCounter, 3d);
        customerBox.getChildren().add(cashierCounter);
    }

    private void initBases() throws FileNotFoundException {
        final File ingFile = new File(ingredientsFilepath);
        final Scanner scanner = new Scanner(ingFile);
        final Gson gson = new Gson();
        String content = "";
        Type listType = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        
        while(scanner.hasNextLine())
            content += scanner.nextLine();
        
        ArrayList<BaseIngredient> bases = gson.fromJson(content, listType);
        
        

        pantry.getChildren().addAll(bases);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dragPane.setPickOnBounds(false);
        initCounters();
        try {
            initBases();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setWorkstation("bowl");
    }
}