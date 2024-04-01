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
    
    private ArrayList<Node> showBaseIngredients(Ingredient[] baseIngredientArray) {

        ArrayList<Node> ingredientList = new ArrayList<>();

        for (Ingredient baseIngredient : baseIngredientArray) {
            ImageView ingredientNode = new ImageView();
            ingredientNode.setImage(baseIngredient.getImage());
            ingredientNode.setId("base-" + baseIngredient.getName());
            ingredientList.add(makeCloneable(ingredientNode));
        }

        pantry.getChildren().addAll(ingredientList);

        return ingredientList;
    }

    private Node makeCloneable(final Node node) {
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
            System.out.println("AAA");
            ImageView child = new ImageView();
            child.setImage(((ImageView) node).getImage());
            child.setId(node.getId().split("-")[1]);
            child.setLayoutX(node.localToScene(node.getBoundsInLocal()).getMinX());
            child.setLayoutY(node.localToScene(node.getBoundsInLocal()).getMinY());

            dragPane.getChildren().add(makeDraggable(child));

            child.requestFocus();
        });

        return node;
    }

    private Node makeDraggable(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.addEventFilter(MouseEvent.ANY, (final MouseEvent mouseEvent) -> {
            mouseEvent.consume();
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            activeItem = (ImageView)node;
            
            dragContext.mouseAnchorX = mouseEvent.getX();
            dragContext.mouseAnchorY = mouseEvent.getY();
            dragContext.initialTranslateX
                    = node.getTranslateX();
            dragContext.initialTranslateY
                    = node.getTranslateY();
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, (final MouseEvent mouseEvent) -> {
            node.setTranslateX(
                    dragContext.initialTranslateX
                    + mouseEvent.getX()
                    - dragContext.mouseAnchorX);
            node.setTranslateY(
                    dragContext.initialTranslateY
                    + mouseEvent.getY()
                    - dragContext.mouseAnchorY);
        });

        return wrapGroup;
    }

    private static class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dragPane.setPickOnBounds(false);

        Ingredient[] arr = new Ingredient[]{
            new Ingredient("flour", true, false),
            new Ingredient("milk", true, false),
            new Ingredient("sugar", true, false),
            new Ingredient("cheese", true, false),
            new Ingredient("butter", true, false),
            new Ingredient("yeast", true, false),
            new Ingredient("egg", true, false)
        };

        showBaseIngredients(arr);

        customerEnter();
    }

}
