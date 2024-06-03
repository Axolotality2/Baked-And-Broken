package main.MainGame;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Core.CustomerGen.Customer;
import main.Core.CustomerGen.CustomerHandler;
import main.Core.CustomerGen.Difficulty;
import main.Exceptions.UnexpectedOrderException;

/**
 *
 * @author user
 */
public class CustomerAnimator extends CustomerHandler {

    private static final String ASSET_LOC = "/main/Assets/customer.png";
    private final Pane customerPane;
    private final CustomerSprite customerImg;
    private final Point2D startPoint;
    private final Point2D waitPoint;
    private final Point2D endPoint;

    public CustomerAnimator(Difficulty difficulty) {
        super(difficulty);
        startPoint = new Point2D(0d, 0d);
        waitPoint = new Point2D(0d, 0d);
        endPoint = new Point2D(0d, 0d);
        customerPane = new Pane();
        customerImg = new CustomerSprite(new Image(getClass().getResourceAsStream(ASSET_LOC)));
    }

    public CustomerAnimator(CustomerHandler customerHandler) {
        super(customerHandler.getDifficulty());
        startPoint = new Point2D(0d, 0d);
        waitPoint = new Point2D(0d, 0d);
        endPoint = new Point2D(0d, 0d);
        customerPane = new Pane();
        customerImg = new CustomerSprite(new Image(getClass().getResourceAsStream(ASSET_LOC)));
    }
    
    private class CustomerSprite extends Receptacle {
        
        private CustomerSprite(Image img) {
            super(img);
        }
        
        @Override
        public void put(DraggableIngredient ingredient) {
            if (ingredient.getIngredient().toProduct() != null) {
                getCue().get(0).score(ingredient.getIngredient().toProduct());
                DraggableIngredient.getDragPane().getChildren().remove(ingredient);
            } else {
                throw new UnexpectedOrderException();
            }
        }
        
    }

    @Override
    protected void shiftIn() {
        if (!isStarted()) {
            return;
        }

        Timer timer = new Timer();

        try {
            customerPane.getChildren().add(customerImg);
            customerImg.setLayoutX(startPoint.getX());
            customerImg.setLayoutY(startPoint.getY());
            translate(startPoint, endPoint, 500);
            
            timer.schedule( // Schedule leave
                    new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            shiftOut();
                        }
                    });
                }
            },
                    getCue().get(0).getOrder().getPrepTime());
        } catch (IndexOutOfBoundsException ex) {
            insertCustomer();
            shiftIn();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void shiftOut() {
        translate(waitPoint, endPoint, 500);
        getLevelScore().add(getCue().get(0).getScore());
        getCue().remove(0);
        getCue().add(new Customer(getDifficulty()));
        shiftIn();
    }

    private void translate(Point2D start, Point2D end, double millis) {
        TranslateTransition enter = new TranslateTransition(Duration.millis(millis), customerImg);
        enter.setDelay(Duration.millis(500));
        enter.setInterpolator(Interpolator.EASE_IN);
        enter.setFromX(start.getX());
        enter.setFromY(start.getY());
        enter.setToX(end.getX());
        enter.setToY(end.getY());
        enter.play();

        enter.setOnFinished(e -> {
            customerImg.setLayoutX(end.getX());
            customerImg.setLayoutY(end.getY());
        });
    }
    
    /**
     *
     * @return the customer ImageView
     */
    public ImageView getCustomer() {
        return customerImg;
    }
}
