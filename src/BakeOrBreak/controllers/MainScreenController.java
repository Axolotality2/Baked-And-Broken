package BakeOrBreak.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import BakeOrBreak.model.*;
import javafx.scene.control.Label;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

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

    private final String ingFilepath = "src/BakeOrBreak/constants/ingredients.json";
    private Countertop kitchenCounter;
    private Countertop cashierCounter;
    private Workstation activeWorkstation;
    private GameMngr gameMngr = GameMngr.getGameManager();

    private void setWorkstation(String name) {
        Workstation bowlView = new Workstation(name);

        double centerPlacement = 3 * Math.round((kitchenCounter.getBoundsInParent().getWidth() - bowlView.getImage().getWidth()) / 6);
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

    private void initBases() throws FileNotFoundException, IOException {
        BaseIngredient.initINGREDIENTS(ingFilepath);
        BaseIngredient.show(pantry, dragPane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dragPane.setPickOnBounds(false);
        initCounters();
        try {
            initBases();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        setWorkstation("bowl");
    }
}