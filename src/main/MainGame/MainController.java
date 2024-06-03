package main.MainGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.Core.CustomerGen.Difficulty;
import main.Core.CustomerGen.Product;
import main.Core.FoodProcessing.Workstation;

public class MainController implements Initializable {

    @FXML   private Pane dragPane;
    @FXML   private AnchorPane customerBox;
    @FXML   private AnchorPane counterPane;
    @FXML   private FlowPane iconTray;
    @FXML   private FlowPane pantry;
    @FXML   private SplitPane splitchen;
    private final Rectangle customerBoxDimensions = new Rectangle(111, 258);
    private CustomerAnimator animator;
    
    private Countertop initCounter(String dir, AnchorPane counterLocation) {
        Countertop counter = new Countertop(new Image(getClass().getResourceAsStream(dir)));
        
        AnchorPane.setBottomAnchor(counter, 0d);
        AnchorPane.setLeftAnchor(counter, 0d);
        
        counterLocation.getChildren().add(counter);
        return counter;
    }
    
    private void initWorkstation(String name) {
        new WorkstationSprite(new Workstation(name)).draw();
    }
    
    public void start(Difficulty difficulty) {
        if (animator == null) {
            animator = new CustomerAnimator(difficulty);
        }
        
        animator.start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dragPane.setPickOnBounds(false);
        
        customerBox.setClip(customerBoxDimensions);
        Countertop kitchenTop = initCounter("/main/Assets/kitchenTable.png", counterPane);
        initCounter("/main/Assets/cashierTable.png", customerBox);
        
        WorkstationSprite.setCounterPane(counterPane);
        WorkstationSprite.setIconTray(iconTray);
        WorkstationSprite.setBottomOffset(48);
        initWorkstation("bowl");
        initWorkstation("blender");
        initWorkstation("oven");
        WorkstationSprite.getStationSprites().get(0).activate();
        
        CloneableIngredient.setBASIC_INGREDIENTS("src/main/Constants/baseIngredients.json");
        CloneableIngredient.fillPantry(pantry);
        
        DraggableIngredient.setDragPane(dragPane);
        DraggableIngredient.setDropPoint(Point2D.ZERO);
        kitchenTop.setSP(splitchen);
        
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        HashSet<Product> prods = new HashSet<>();
//        prods.add(new Product("Pandesal", new String[] {"egg", "dairy", "wheat"}, 1, 30000));
//        
//        System.out.println(gson.toJson(
//                prods
//        ));
        
        Product.setValidProducts("src/main/Constants/products.json");
    }
}
