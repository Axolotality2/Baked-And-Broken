package q3aa2_tau_regaladorm.model;

public class Denser extends Level {
    public Denser(WeightedDist<Integer> complexityTable, WeightedDist<Integer> orderSizeTable, WeightedDist<Float> timeMultTable, int initialCustomerCount) {
        super(complexityTable, orderSizeTable, initialCustomerCount);
        
        for (Customer c : this.customers)
            c.setLeaveTime(c.getLeaveTime() * timeMultTable.pickRandom());
    }
}
