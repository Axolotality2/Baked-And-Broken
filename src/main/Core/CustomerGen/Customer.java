package main.Core.CustomerGen;

import main.Core.FoodProcessing.Ingredient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import main.Exceptions.UnexpectedOrderException;

/**
 *
 * @author user
 */
public class Customer {

    private final Receipt order;
    private final Set<Ingredient> allergies;
    private long orderTime;
    private Score score;
    private CustomerHandler handler;

    public Customer(Receipt order, Ingredient[] allergies) {
        this.order = order;
        this.orderTime = System.currentTimeMillis();
        this.allergies = new HashSet<>(Arrays.asList(allergies));
        this.score = new Score();
    }
    
    public Customer(Difficulty difficulty) {
        this.order = new Receipt(difficulty);
        this.orderTime = System.currentTimeMillis();
        this.allergies = difficulty.allergies();
    }

    public void score(Product product) throws IllegalArgumentException {
        if (!order.getOrder().contains(product)) {
            throw new UnexpectedOrderException();
        }
        
        Set<Ingredient> productAllergens = new HashSet<>(product.getAllergens());
        int speed = (int) Math.ceil(100 * (orderTime + order.getPrepTime() - System.currentTimeMillis()) / order.getPrepTime()); 
        int rating = 1;
        
        rating += productAllergens.retainAll(allergies) ? -2 : 1; // Will it cause a reaction?
        rating += speed > 10 ? 1 : 0;
        rating += speed > 30 ? 2 : 0;
        
        score.addScore(rating, speed);
        orderTime = System.currentTimeMillis();
        order.getOrder().remove(product);
        
        if (order.getOrder().isEmpty()) {            
            handler.shiftOut();
        }
    }
    
    /**
     * @return the order
     */
    public Receipt getOrder() {
        return order;
    }

    /**
     * @return the allergies
     */
    public Set<Ingredient> getAllergies() {
        return allergies;
    }

    /**
     * @return the score
     */
    public Score getScore() {
        return score;
    }

    /**
     * @return the handler
     */
    public CustomerHandler getHandler() {
        return handler;
    }

    /**
     * @param handler the handler to set
     */
    public void setHandler(CustomerHandler handler) {
        this.handler = handler;
    }
}
