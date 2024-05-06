package BakeOrBreak.model;

public class LevelData {

    protected final Difficulty difficulty;
    private int customersServed;
    private int totalRating;
    private int totalSpeed;
    private Customer customer;
    
    public LevelData(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public int getCustomersServed() {
        return customersServed;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public int getTotalSpeed() {
        return totalSpeed;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Customer getCustomer() {
        return customer;
    }
}
