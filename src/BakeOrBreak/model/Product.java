package BakeOrBreak.model;

import java.util.ArrayList;

public class Product extends DragIngredient {

    public static final Product[] ORDERABLE = new Product[]{};
    private static final int BASE_TIME = 30, TIME_ADDITION = 10;
    private final int complexity;
    private final int prepTimeSec;

    // Used for converting intermediates into complete products (i.e. Step)
    public Product(DragIngredient ingredient) {
        super(ingredient.getIngredientData());
        this.complexity = 0;
        this.prepTimeSec = 0;
    }
    
    // Used for selecting products (i.e. Customer)
    public Product(IngredientData ingredientData, int complexity) {
        super(ingredientData);
        this.complexity = complexity;
        this.prepTimeSec = BASE_TIME + TIME_ADDITION * complexity;
    }

    public static Product[] filterByComplexity(int complexity, int margin) {
        ArrayList<Product> filtered = new ArrayList<>();
        
        for (Product p : ORDERABLE)
            if (p.complexity <= complexity + margin && p.complexity >= complexity - margin)
                filtered.add(p);
        
        return (Product[]) filtered.toArray();
    }
    
    public static Product[] filterByComplexity(int complexity) {
        return filterByComplexity(complexity, 0);
    }
    
    public int getComplexity() {
        return complexity;
    }
    
    public int getPrepTime() {
        return prepTimeSec;
    }
}