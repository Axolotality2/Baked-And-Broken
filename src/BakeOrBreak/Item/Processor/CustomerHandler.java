package BakeOrBreak.Item.Processor;

import BakeOrBreak.GameData.Difficulty;
import BakeOrBreak.GameData.LevelStatistic;
import java.util.Timer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CustomerHandler {

    private Pane customerPane;
    private Customer customer;
    private Difficulty difficulty;
    private Label objectiveLabel;
    private LevelStatistic levelData;
    private final long startTime;

    public CustomerHandler(Difficulty difficulty) {
        startTime = System.currentTimeMillis();
        this.difficulty = difficulty;
    }
    
    public CustomerHandler setCustomerPane(Pane customerPane) {
        CustomerHandler customerHandler = new CustomerHandler(difficulty);
        customerHandler.customerPane = customerPane;
        return customerHandler;
    }
    
    public CustomerHandler setObjectiveLabel(Label objectiveLabel) {
        CustomerHandler customerHandler = new CustomerHandler(difficulty);
        customerHandler.objectiveLabel = objectiveLabel;
        return customerHandler;
    }

    public long getTimeMillis() {
        return System.currentTimeMillis() - startTime;
    }

    private Customer makeCustomer() {
        
    }
    
    public void add(Customer newCustomer) {
        customer = newCustomer;
        Duration duration = Duration.millis(500);
        
        customerPane.getChildren().add(customer);
        customer.setLayoutX(-customer.getBoundsInParent().getWidth());
        customer.setLayoutY(162 - customer.getBoundsInParent().getHeight());

        TranslateTransition enterTrans = new TranslateTransition(duration, customer);
        enterTrans.setInterpolator(Interpolator.EASE_BOTH);
        enterTrans.setByX(
                (customer.getBoundsInParent().getWidth() 
                + customer.getBoundsInParent().getWidth()) / 2);
        enterTrans.play();

        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    removeCustomer();
                });
            }
        }, (long) customer.getPrepTime());

        try {
            objectiveLabel.setText("Make a " + customer.getOrder().get(0).getName());
        } catch (IndexOutOfBoundsException ex) {
            objectiveLabel.setText("...");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            objectiveLabel.setText("???");
            System.out.println(ex.getMessage());
        }
    }

    public void remove() {
        levelData.getLevelStatistics().add(customer.getCustomerStatistic());
        Duration duration = Duration.millis(500);
        
        TranslateTransition enterTrans = new TranslateTransition(duration, customer);
        enterTrans.setInterpolator(Interpolator.EASE_BOTH);
        enterTrans.setByX((customerPane.getBoundsInParent().getWidth() + customer.getBoundsInParent().getWidth()) / 2);
        enterTrans.play();

        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
            @Override
            public void run() { // EEEEEEERRRRRRROOOOOOOOOOOORR !!
                Platform.runLater(() -> {
                    objectiveLabel.setText("");
                    customerPane.getChildren().remove(customer);
                });
            }
        }, (long) duration.toMillis());
    }
}
