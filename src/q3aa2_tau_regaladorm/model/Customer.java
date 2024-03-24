package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Customer {
    
    private final int orderTime, orderSize;
    private final ArrayList<Product> order;
    private final Ingredient[] allergies;
    private int totalRating, leaveTime;

    public Customer(WeightedDist<Integer> complexityTable, int orderSize, Ingredient[] allergies, int leaveTime) {
        this.order = getOrder(complexityTable, orderSize);
        this.totalRating = this.orderSize = this.order.size();
        this.allergies = allergies;
        this.leaveTime = leaveTime;
        this.orderTime = PlayerManager.getCurrTime();
    }
    
    public Customer(WeightedDist<Integer> complexityTable, int orderSize, Ingredient[] allergies) {
        this(complexityTable, orderSize, allergies, PlayerManager.getCurrTime());
        for (Product p : order)
            this.leaveTime += p.getPrepTime();
    }

    public Customer(ArrayList<Product> order, Ingredient[] allergies, int leaveTime) {
        this.order = order;
        this.totalRating = this.orderSize = this.order.size();
        this.allergies = allergies;
        this.leaveTime = leaveTime;
        this.orderTime = PlayerManager.getCurrTime();
    }
    
    public Customer(ArrayList<Product> order, Ingredient[] allergies) {
        this(order, allergies, PlayerManager.getCurrTime());
        for (Product p : order)
            this.leaveTime += p.getPrepTime();
    }

    private ArrayList<Product> getOrder(WeightedDist<Integer> complexityTable, int variation, int orderSize) {
        ArrayList<Product> orderList = new ArrayList<>();
        Product[] orderable;
        int index;
        
        for (int i = 0; i < orderSize; i++) {
            orderable = Product.filterByComplexity(complexityTable.pickRandom(), variation); 
            index = (int) (Math.random() * (orderable.length - 1));
            orderList.add(orderable[index]);
        }

        return orderList;
    }

    private ArrayList<Product> getOrder(WeightedDist<Integer> complexityTable, int orderSize) {
        return this.getOrder(complexityTable, 0, orderSize);
    }

    public int rateProduct(Product product, int currentTime) throws Exception {
        int score = 1;
        int percentTimeSpent = (currentTime - orderTime) / (currentTime - leaveTime);
        boolean allergenFree = true;

        if (!order.remove(product)) { // Remove from remaining order
            throw new Exception();
        }

        for (Ingredient allergy : allergies) // Test for allergies
        {
            if (Arrays.asList(product.getAllergens()).contains(allergy)) {
                allergenFree = false;
            }
        }

        if (percentTimeSpent < 0.90) {
            score++;
        }
        if (percentTimeSpent < 0.60) {
            score++;
        }
        if (allergenFree) {
            score++;
        }

        this.totalRating += score;
        return score;
    }

    public float getOverallRating() {
        return totalRating / orderSize;
    }

    public int getLeaveTime() {
        return leaveTime;
    }

    public ArrayList<Product> getOrder() {
        return order;
    }
    
    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }
    
    public void setLeaveTime(float multiplier) {
        this.leaveTime *= multiplier;
    }
}