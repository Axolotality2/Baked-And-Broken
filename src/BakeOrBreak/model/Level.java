package BakeOrBreak.model;

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
        
        for (int i = 0; i < difficulty.getRandCustomerCount(); i++)
            addCustomer();
    }
    
    public Level(Difficulty difficulty, int initialCustomerCount) {
        this.difficulty = difficulty;
        this.customers = new ArrayList<>();
        
        for (int i = 0; i < initialCustomerCount; i++)
            addCustomer();
    }
    
    protected final void addCustomer() {
        customers.add(new Customer(difficulty));
    }

    protected void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void removeCustomer() throws IndexOutOfBoundsException {
        removeCustomer(0);
    }
    
    public void removeCustomer(int index) throws IndexOutOfBoundsException {
        Customer customerFound = customers.get(index);
        
        totalSpeed += customerFound.getOverallSpeed();
        totalRating += customerFound.getOverallRating();
        customers.remove(index);
        
        if (index == 0) {
            currentCustomer = customers.get(index);
        }
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
