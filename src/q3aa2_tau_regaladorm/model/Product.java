package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;

public class Product extends Ingredient implements Draggable {

    public static final Product[] ORDERS = new Product[]{};
    private static final int BASE_TIME = 30, TIME_ADDITION = 10;
    private final int complexity;
    private final int prepTimeSec;

    public Product(String name, Step[] steps, boolean isFood, Ingredient[] allergens, int complexity) {
        super(name, steps, isFood, allergens);
        
        this.complexity = complexity;
        this.prepTimeSec = BASE_TIME + TIME_ADDITION * complexity;
    }

    public static Product[] filterByComplexity(int complexity, int margin) {
        ArrayList<Product> filtered = new ArrayList<>();
        
        for (Product p : ORDERS)
            if (p.complexity <= complexity + margin && p.complexity >= complexity - margin)
                filtered.add(p);
        
        return (Product[]) filtered.toArray();
    }
    
    public static Product[] filterByComplexity(int complexity) {
        return filterByComplexity(complexity, 0);
    }
    
    @Override
    public void click() {

    }

    @Override
    public void drag() {

    }

    public String getName() {
        return name;
    }

    public Step[] getSteps() {
        return steps;
    }

    public boolean isFood() {
        return isFood;
    }

    public int getComplexity() {
        return complexity;
    }
    
    public int getPrepTime() {
        return prepTimeSec;
    }
    
    public Ingredient[] getAllergens() {
        return allergens;
    }
}
