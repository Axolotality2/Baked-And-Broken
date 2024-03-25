package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;

public class Level {

    protected final ArrayList<Customer> customers;
    protected final Difficulty difficulty;
    private int customersServed;
    private int totalRating;
    private int totalSpeed;
    private Customer currentCustomer;
    
    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.customers = new ArrayList<>();
        
        for (int i = 0; i < difficulty.getCustomerCountTable().pickRandom(); i++)
            addCustomer();
    }
    
    public Level(Difficulty difficulty, int initialCustomerCount) {
        this.difficulty = difficulty;
        this.customers = new ArrayList<>();
        
        for (int i = 0; i < initialCustomerCount; i++)
            addCustomer();
    }
    
    protected void addCustomer() {
        customers.add(new Customer(difficulty));
    }

    protected void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
    
    public void removeCustomer(Customer customer) throws Exception {
        if (!customers.remove(customer))
            throw new Exception();
        
        totalSpeed += customer.getOverallSpeed();
        totalRating += customer.getOverallRating();
    }

    public void removeCustomer(int index) throws Exception {
        if (!customers.remove(customers.get(index)))
            throw new Exception();
        
        totalSpeed += customers.get(index).getOverallSpeed();
        totalRating += customers.get(index).getOverallRating();
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
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
}
