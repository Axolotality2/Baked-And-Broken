package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;

public class Level {

    protected static final WeightedDist<Integer> ALLERGY_COUNT = new WeightedDist<>();
    protected static final int CLOSING_TIME = 600;
    protected final WeightedDist<Integer> complexityTable, orderSizeTable;
    protected final ArrayList<Customer> customers;
    protected int time, customersServed;
    protected Customer currentCustomer;

    public Level(WeightedDist<Integer> complexityTable, WeightedDist<Integer> orderSizeTable, int initialCustomerCount) {
        this.complexityTable = complexityTable;
        this.orderSizeTable = orderSizeTable;
        this.time = 0;
        this.customers = cueCustomers(initialCustomerCount);
    }

    private ArrayList<Customer> cueCustomers(int customerCount) {
        ArrayList<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < customerCount; i++) {
            int orderSize = this.orderSizeTable.pickRandom();
            Ingredient[] allergies = Ingredient.ALLERGENS.pickRandom(ALLERGY_COUNT.pickRandom());

            customerList.add(new Customer(complexityTable, orderSize, allergies));
        }

        return customerList;
    }

    public void removeCustomer(Customer customer) throws Exception {
        if (!customers.remove(customer))
            throw new Exception();
    }

    public void removeCustomer(int index) throws Exception {
        if (!customers.remove(customers.get(index)))
            throw new Exception();
    }

    public final void updateTime() {
        time++;

        if (time >= CLOSING_TIME) {
            PlayerManager.getCurrWeek().endDay();
        }
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public int getTime() {
        return time;
    }

    public int getCustomersServed() {
        return customersServed;
    }
}
