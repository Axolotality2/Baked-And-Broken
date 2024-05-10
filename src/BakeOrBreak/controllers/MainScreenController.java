package BakeOrBreak.controllers;

import BakeOrBreak.GameData.Difficulty;
import BakeOrBreak.Item.Processor.Workstation;
import BakeOrBreak.Item.Processor.Countertop;
import BakeOrBreak.Item.Processor.Customer;
import BakeOrBreak.GameData.LevelStatistic;
import BakeOrBreak.Item.DragIngredient;
import BakeOrBreak.Item.BaseIngredient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Timer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    private ImageView taskbar;
    @FXML
    private Label objective;

    private Countertop kitchenCounter;
    private Countertop cashierCounter;
    private ArrayList<Workstation> workstationList = new ArrayList<>();
    private Workstation activeWorkstation;
    private LevelStatistic levelData = new LevelStatistic(new Difficulty(0));
    private final Rectangle customerBoxDimensions = new Rectangle(111, 258);

    private void addWorkstation(String... names) {
        for (String name : names) {
            activeWorkstation = new Workstation(name);

            double centerPlacement = 3 * Math.round((kitchenCounter.getBoundsInParent().getWidth() - activeWorkstation.getImage().getWidth()) / 6);
            double heightPlacement = kitchenCounter.getBoundsInParent().getHeight() - 15;

            AnchorPane.setLeftAnchor(activeWorkstation, centerPlacement);
            AnchorPane.setBottomAnchor(activeWorkstation, heightPlacement);
            workstationList.add(activeWorkstation);
        }

        taskbar.toFront();
    }

    public void setWorkstation(ActionEvent event) {
        setWorkstation(((Node) event.getSource()).getId().split("_")[0]);
    }

    private void setWorkstation(String name) {
        counterPane.getChildren().removeAll(workstationList);

        for (Workstation w : workstationList) {
            if (w.getName().equals(name)) {
                counterPane.getChildren().add(w);
                taskbar.toFront();
                objective.toFront();
            }
        }
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

        AnchorPane.setBottomAnchor(cashierCounter, 0d);
        AnchorPane.setLeftAnchor(cashierCounter, 0d);
        customerBox.getChildren().add(cashierCounter);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dragPane.setPickOnBounds(false);

        customerBox.setClip(customerBoxDimensions);
        initCounters();
        DragIngredient.setDragPane(dragPane);
        BaseIngredient.setDragPane(dragPane);
        BaseIngredient.show(pantry);
        addWorkstation("bowl", "blender", "oven");
        setWorkstation("bowl");
        
        new Customer(new ArrayList<>(), new String[0], 5).addCustomer();
    }
}
