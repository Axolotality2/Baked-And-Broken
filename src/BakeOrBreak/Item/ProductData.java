package BakeOrBreak.Item;

public class ProductData extends IngredientData {
    
    private static final int BASE_TIME = 30, TIME_ADDITION = 5;
    private final int complexity;
    private final int prepTimeSec;
    
    public ProductData(String name, boolean isFood, String[] allergens, int complexity, int prepTimeSec) {
        super(name, isFood, allergens);
        this.complexity = complexity;
        this.prepTimeSec = prepTimeSec;
    }
    
    public ProductData(IngredientData ingredientData, int complexity, int prepTimeSec) {
        super(ingredientData);
        this.complexity = complexity;
        this.prepTimeSec = prepTimeSec;
    }
    
    public ProductData(IngredientData ingredientData, int complexity) {
        super(ingredientData);
        this.complexity = complexity;
        this.prepTimeSec = BASE_TIME + TIME_ADDITION * complexity;
    }

    public int getComplexity() {
        return complexity;
    }

    public int getPrepTimeSec() {
        return prepTimeSec;
    }
}
