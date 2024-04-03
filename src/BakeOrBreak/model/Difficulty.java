package BakeOrBreak.model;

public class Difficulty {

    private final WeightedDist<Integer> complexityTable, orderSizeTable, allergyTable, customerCountTable;
    private static int difficulties = 0;
    public static final Difficulty EASY = null; 
    public static final Difficulty MEDIUM = null;
    public static final Difficulty HARD = null;

    public Difficulty(WeightedDist<Integer> c, WeightedDist<Integer> o, WeightedDist<Integer> a, WeightedDist<Integer> cc) {
        this.complexityTable = c;
        this.orderSizeTable = o;
        this.allergyTable = a;
        this.customerCountTable = cc;
        difficulties++;
    }

    public WeightedDist<Integer> getComplexityTable() {
        return complexityTable;
    }

    public WeightedDist<Integer> getOrderSizeTable() {
        return orderSizeTable;
    }

    public WeightedDist<Integer> getAllergyTable() {
        return allergyTable;
    }
    
    public WeightedDist<Integer> getCustomerCountTable() {
        return customerCountTable;
    }
}
