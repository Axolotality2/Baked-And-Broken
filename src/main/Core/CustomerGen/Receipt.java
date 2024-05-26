package main.Core.CustomerGen;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Receipt {
    
    private final ArrayList<Product> order;
    private long prepTime;
    
    public Receipt(Product... order) {
        this.order = new ArrayList<>();
        prepTime = 0;
        
        for (Product item : order) {
            this.order.add(item);
            prepTime += item.getPrepTimeMillis();
        }
    }
    
    public Receipt(int orderSize, int complexity) {
        order = new ArrayList<>();
        prepTime = 0;

        for (int i = orderSize; i > 0; i--) {
            Product product = Product.getProduct();
            
            order.add(product);
            prepTime += product.getPrepTimeMillis();
        }
    }
    
    public Receipt(Difficulty difficulty) {
        order = new ArrayList<>();
        prepTime = 0;
        
        for (int i = difficulty.orderSize(); i > 0; i--) {
            order.add(Product.getProduct());
        }
    }

    /**
     * @return the order
     */
    public ArrayList<Product> getOrder() {
        return new ArrayList<>(order);
    }
    
    /**
     * @return the prepTime
     */
    public long getPrepTime() {
        return prepTime;
    }
}
